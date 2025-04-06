package fr.unice.pizzaproject.model

data class OrderItem(
    val orderItemId: Long = 0,  // Correspond à la clé primaire dans la base de données
    val orderId: Long,          // Clé étrangère vers la table Order
    val pizzaId: Int,           // Clé étrangère vers la table Pizza
    val quantity: Int,
    val extraCheese: Float = 0f // Quantité de fromage supplémentaire
)
