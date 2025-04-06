package fr.unice.pizzaproject.viewmodel

import fr.unice.pizzaproject.data.DataSource
import fr.unice.pizzaproject.repository.PizzaRepository


import fr.unice.pizzaproject.model.Pizza

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PizzaViewModel(private val pizzaRepository: PizzaRepository) {

    private val dataSource = DataSource() // Source de données pour charger les pizzas

    private val _pizzaList = MutableStateFlow<List<Pizza>>(emptyList())
    val pizzaList: StateFlow<List<Pizza>> = _pizzaList

    private val _selectedPizza = MutableStateFlow<Pizza?>(null)
    val selectedPizza: StateFlow<Pizza?> = _selectedPizza

    // Liste pour le panier
    private val _cartItems = MutableStateFlow<List<Pizza>>(emptyList())
    val cartItems: StateFlow<List<Pizza>> = _cartItems

    // Coroutine Scope pour exécuter les opérations en arrière-plan (remplace ViewModelScope d'Android)
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    init {
        // Charger les pizzas dès l'initialisation
        viewModelScope.launch {
            loadPizzas()
        }
    }

    // Charger les pizzas depuis la source de données
    public suspend fun loadPizzas() {
        val pizzas = dataSource.loadPizzas() // Récupérer la liste de pizzas depuis DataSource
        _pizzaList.value = pizzas
        pizzaRepository.loadPizzasIfNeeded(pizzas)
    }

    fun addToCart(pizza: Pizza, extraCheese: Float) {
        val updatedIngredients = if (extraCheese > 0) {
            val cheeseCount = extraCheese.toInt() // Nombre de portions de fromage en extra
            pizza.ingredients + List(cheeseCount) { "Extra Cheese" }
        } else {
            pizza.ingredients
        }

        val updatedPizza = pizza.copy(ingredients = updatedIngredients)
        _cartItems.value = _cartItems.value + updatedPizza
    }

    // Supprimer une pizza du panier
    fun removeFromCart(pizza: Pizza) {
        _cartItems.value = _cartItems.value.filterNot { it.id == pizza.id }
    }

    // Modifier les ingrédients d'une pizza
    fun updateIngredients(pizzaId: Int, newIngredients: List<String>) {
        _pizzaList.value = _pizzaList.value.map {
            if (it.id == pizzaId) it.copy(ingredients = newIngredients) else it
        }
        _selectedPizza.value = _pizzaList.value.find { it.id == pizzaId }
    }

    // Sélectionner une pizza
    fun selectPizza(pizza: Pizza) {
        _selectedPizza.value = pizza
    }
}
