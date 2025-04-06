package fr.unica.miage.donati.pizzaapp.model

data class Order(
    val orderId: Long = 0,
    val orderDate: Long,  // Timestamp de la commande
    val totalAmount: Double,  // Montant total de la commande
    val status: String  // Statut de la commande, comme "En préparation", "Livrée", etc.
)
