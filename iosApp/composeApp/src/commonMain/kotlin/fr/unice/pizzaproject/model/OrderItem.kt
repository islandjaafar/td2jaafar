package fr.unice.pizzaproject.model


data class OrderItem(
    val orderItemId: Long = 0,
    val orderId: Long,  // Clé étrangère
    val pizzaId: Int,
    val quantity: Int,
    val extraCheese: Float = 0f  // Quantité de fromage supplémentaire
)
