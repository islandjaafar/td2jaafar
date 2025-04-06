package fr.unice.pizzaproject.data

import fr.unice.pizzaproject.model.Pizza

expect class DataSource {
    fun loadPizzas(): List<Pizza>
    fun getImagePath(imageName: String): Int // Any permet de retourner le bon type selon la plateforme
}