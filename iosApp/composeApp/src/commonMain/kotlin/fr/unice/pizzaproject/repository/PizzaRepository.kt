package fr.unice.pizzaproject.repository

import fr.unice.pizzaproject.database.PizzaQueries
import fr.unice.pizzaproject.model.Pizza

class PizzaRepository(
    private val pizzaQueries: PizzaQueries
) {

    // Insérer une pizza
    fun insertPizza(pizza: Pizza) {
        pizzaQueries.insertPizza(
            id = null,  // Laissez null si AUTOINCREMENT est utilisé
            name = pizza.name,
            imageRes = pizza.imageRes,
            ingredients = pizza.ingredients.joinToString(","),
            price = pizza.price.toDouble()
        )
    }

    // Récupérer toutes les pizzas
    fun getAllPizzas(): List<Pizza> {
        return pizzaQueries.selectAllPizzas()
            .executeAsList()
            .map {
                Pizza(
                    id = it.id.toInt(),
                    name = it.name,
                    imageRes = it.imageRes,
                    ingredients = it.ingredients.split(",").map { ingredient -> ingredient.trim() },
                    price = it.price.toDouble()
                )
            }
    }

    // Récupérer une pizza par ID
    fun getPizzaById(pizzaId: Int): Pizza? {
        return pizzaQueries.selectPizzaById(pizzaId.toLong())
            .executeAsOneOrNull()
            ?.let {
                Pizza(
                    id = it.id.toInt(),
                    name = it.name,
                    imageRes = it.imageRes,
                    ingredients = it.ingredients.split(",").map { ingredient -> ingredient.trim() },
                    price = it.price.toDouble()
                )
            }
    }

    // Supprimer une pizza par ID
    fun deletePizzaById(pizzaId: Int) {
        pizzaQueries.deletePizzaById(pizzaId.toLong())
    }

    // Charger les pizzas (si elles n'existent pas, les insérer)
    suspend fun loadPizzasIfNeeded(pizzas: List<Pizza>) {
        pizzas.forEach { pizza ->
            val existingPizza = getPizzaById(pizza.id )
            if (existingPizza == null) {
                // Si la pizza n'existe pas, l'insérer
                insertPizza(pizza)
            }
        }
    }
}
