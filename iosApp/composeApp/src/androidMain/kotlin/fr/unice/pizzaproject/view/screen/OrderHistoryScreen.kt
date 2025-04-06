package fr.unice.pizzaproject.view.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.unica.miage.donati.pizzaapp.viewmodel.OrderViewModel
import fr.unice.pizzaproject.model.Order


@Composable
fun OrderHistoryScreen(orderViewModel: OrderViewModel, navController: NavController) {
    // Observer la liste des commandes
    val orders by orderViewModel.orders.collectAsState()

    // Charger les commandes au démarrage
    orderViewModel.loadOrders()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Historique des Commandes", style = MaterialTheme.typography.headlineSmall)

        // Afficher les commandes dans un LazyColumn pour un scroll infini
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(orders) { order ->
                OrderItem(order = order)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    // Affichage d'une commande
    Surface(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Commande ID: ${order.orderId}", style = MaterialTheme.typography.bodyLarge)
            Text("Date: ${order.orderDate}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Text("Total: ${order.totalAmount}€", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

