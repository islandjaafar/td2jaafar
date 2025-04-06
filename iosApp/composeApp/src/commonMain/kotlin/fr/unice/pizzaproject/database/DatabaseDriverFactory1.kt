package fr.unice.pizzaproject.database


import fr.unice.pizzaproject.model.Order
import fr.unice.pizzaproject.model.OrderItem
import app.cash.sqldelight.db.SqlDriver
import fr.unice.pizzaproject.model.Pizza

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

