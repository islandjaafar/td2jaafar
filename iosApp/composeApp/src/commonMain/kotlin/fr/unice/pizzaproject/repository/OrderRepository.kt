package fr.unice.pizzaproject.repository


import fr.unice.pizzaproject.database.DatabaseManager
import fr.unice.pizzaproject.model.Order
import fr.unice.pizzaproject.model.OrderItem

class OrderRepository(
    private val dbManager: DatabaseManager
) {

    // Insérer une commande
    fun insertOrder(order: Order) {
        dbManager.orderQueries.insertOrder(
            orderDate = order.orderDate,
            totalAmount = order.totalAmount,
            status = order.status
        )
        // Récupérer l'ID de la dernière insertion
       // return dbManager.orderQueries.lastInsertRowId().executeAsOne()
        // Récupérer l'ID de la commande insérée

    }

    // Insérer des items de commande (les pizzas dans la commande)
    fun insertOrderItems(orderItems: List<OrderItem>) {
        orderItems.forEach { item ->
            dbManager.orderItemQueries.insertOrderItem(
                orderId = item.orderId,
                pizzaId = item.pizzaId.toLong(),
                quantity = item.quantity.toLong(),
                extraCheese = item.extraCheese.toDouble()
            )
        }
    }

    // Récupérer les items d'une commande par son ID
    fun getOrderItemsByOrderId(orderId: Long): List<OrderItem> {
        return dbManager.orderItemQueries.selectOrderItemsByOrderId(orderId)
            .executeAsList()
            .map {
                OrderItem(
                    orderItemId = it.orderItemId,
                    orderId = it.orderId,
                    pizzaId = it.pizzaId.toInt(),
                    quantity = it.quantity.toInt(),
                    extraCheese = it.extraCheese.toFloat()
                )
            }
    }

    // Récupérer une commande par son ID
    fun getOrderById(orderId: Long): Order? {
        return dbManager.orderQueries.selectOrderById(orderId)
            .executeAsOneOrNull()
            ?.let {
                Order(
                    orderId = it.orderId,
                    orderDate = it.orderDate,
                    totalAmount = it.totalAmount,
                    status = it.status
                )
            }
    }

    // Récupérer toutes les commandes
    fun getAllOrders(): List<Order> {
        return dbManager.orderQueries.selectAllOrders()
            .executeAsList()
            .map {
                Order(
                    orderId = it.orderId,
                    orderDate = it.orderDate,
                    totalAmount = it.totalAmount,
                    status = it.status
                )
            }
    }
    fun returnLastInsertedOrderId(): Long {
        return dbManager.orderQueries.getLastInsertedOrderId()
            .executeAsOne()
    }
}
