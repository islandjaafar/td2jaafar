package fr.unica.miage.donati.pizzaapp.viewmodel

import androidx.lifecycle.ViewModel
import fr.unice.pizzaproject.data.DataSource


import fr.unice.pizzaproject.model.Pizza
import fr.unice.pizzaproject.repository.PizzaRepository


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PizzaViewModel(private val pizzaRepository: PizzaRepository) : ViewModel() {

    private val dataSource = DataSource() // Source de données pour charger les pizzas

    private val _pizzaList = MutableStateFlow<List<Pizza>>(emptyList())
    val pizzaList: StateFlow<List<Pizza>> = _pizzaList

    private val _selectedPizza = MutableStateFlow<Pizza?>(null)
    val selectedPizza: StateFlow<Pizza?> = _selectedPizza

    // Liste pour le panier
    private val _cartItems = MutableStateFlow<List<Pizza>>(emptyList())
    val cartItems: StateFlow<List<Pizza>> = _cartItems

    // Charger les pizzas depuis la source de données
    suspend fun loadPizzas() {
        _pizzaList.value = dataSource.loadPizzas()  // Récupérer la liste de pizzas depuis DataSource
        pizzaRepository.loadPizzasIfNeeded(_pizzaList.value)
    }

    fun addToCart(pizza: Pizza, extraCheese: Float) {
        // Créer une liste d'ingrédients mise à jour avec "Extra Cheese" selon la quantité spécifiée
        val updatedIngredients = if (extraCheese > 0) {
            // Ajouter "Extra Cheese" autant de fois que la quantité spécifiée (en arrondissant à l'entier le plus proche)
            val cheeseCount = extraCheese.toInt() // Utilisation de toInt() pour arrondir le nombre de portions de fromage
            pizza.ingredients + List(cheeseCount) { "Extra Cheese" }
        } else {
            pizza.ingredients // Si aucune quantité de fromage, on garde les ingrédients d'origine
        }

        // Créer une pizza mise à jour avec les nouveaux ingrédients
        val updatedPizza = pizza.copy(ingredients = updatedIngredients)

        // Ajouter la pizza mise à jour au panier
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
        // Mettre à jour la pizza sélectionnée
        _selectedPizza.value = _pizzaList.value.find { it.id == pizzaId }
    }

    // Sélectionner une pizza
    fun selectPizza(pizza: Pizza) {
        _selectedPizza.value = pizza
    }
}
