package fr.unice.pizzaproject.viewmodel

import fr.unice.pizzaproject.repository.OrderRepository
import fr.unice.pizzaproject.model.Order
import fr.unice.pizzaproject.model.OrderItem

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class OrderViewModel(private val orderRepository: OrderRepository) {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    // Desktop ne possède pas ViewModel, on gère manuellement la coroutine
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    // Charger toutes les commandes
    fun loadOrders() {
        viewModelScope.launch {
            _orders.value = orderRepository.getAllOrders()
        }
    }

    // Passer une commande
    fun placeOrder(order: Order, orderItems: List<OrderItem>) {
        viewModelScope.launch {
            orderRepository.insertOrder(order)
            val newOrderId = orderRepository.returnLastInsertedOrderId()
            val orderItemsWithOrderId = orderItems.map { it.copy(orderId = newOrderId) }
            orderRepository.insertOrderItems(orderItemsWithOrderId)
        }
    }

    // Récupérer les items d'une commande spécifique
    fun getOrderItems(orderId: Long): List<OrderItem> {
        return orderRepository.getOrderItemsByOrderId(orderId)
    }
}
