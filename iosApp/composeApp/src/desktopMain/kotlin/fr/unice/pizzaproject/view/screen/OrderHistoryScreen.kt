package fr.unice.pizzaproject.view.screen

import fr.unice.pizzaproject.viewmodel.OrderViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.unice.pizzaproject.model.Order




@Composable
fun OrderHistoryScreen(orderViewModel: OrderViewModel, onBack: () -> Unit) {
    val orders by orderViewModel.orders.collectAsState()

    LaunchedEffect(Unit) {
        orderViewModel.loadOrders()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Historique des Commandes", style = MaterialTheme.typography.h2)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(orders) { order ->
                OrderItem(order)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour")
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Commande ID: ${order.orderId}", style = MaterialTheme.typography.body1)
            Text("Date: ${order.orderDate}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Total: ${order.totalAmount}€", style = MaterialTheme.typography.body1)
        }
    }
}
