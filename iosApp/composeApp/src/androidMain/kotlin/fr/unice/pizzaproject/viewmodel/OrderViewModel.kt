package fr.unica.miage.donati.pizzaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.unice.pizzaproject.model.Order
import fr.unice.pizzaproject.model.OrderItem
import fr.unice.pizzaproject.repository.OrderRepository
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    // Récupérer toutes les commandes passées
    fun loadOrders() {
        viewModelScope.launch {
            _orders.value = orderRepository.getAllOrders() // Fonction à créer dans votre repository pour récupérer toutes les commandes
        }
    }
    fun placeOrder(order: Order, orderItems: List<OrderItem>) {
        viewModelScope.launch {
            // Insertion de la commande et récupération de son ID
            val orderId = orderRepository.insertOrder(order)
            val newOrderId =orderRepository.returnLastInsertedOrderId();
            // Maintenant que l'ID de la commande est récupéré, insérez les items
            val orderItemsWithOrderId = orderItems.map { it.copy(orderId = newOrderId) }
            orderRepository.insertOrderItems(orderItemsWithOrderId)
        }
    }

    // Récupérer les items d'une commande
    fun getOrderItems(orderId: Long) {
        viewModelScope.launch {
            val orderItems = orderRepository.getOrderItemsByOrderId(orderId)
            // Mettre à jour l'UI avec les items de la commande
        }
    }
}
