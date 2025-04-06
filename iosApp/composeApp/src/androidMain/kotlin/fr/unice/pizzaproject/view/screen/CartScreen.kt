package fr.unica.miage.donati.pizzaapp.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.unica.miage.donati.pizzaapp.viewmodel.OrderViewModel
import fr.unica.miage.donati.pizzaapp.viewmodel.PizzaViewModel
import fr.unice.pizzaproject.R
import fr.unice.pizzaproject.model.Order
import fr.unice.pizzaproject.model.Pizza


@Composable
fun CartScreen(viewModel: PizzaViewModel, orderViewModel: OrderViewModel, navController: NavController) {
    // Observer les articles du panier
    val cartItems by viewModel.cartItems.collectAsState()

    // Calculer le prix total
    val totalPrice = cartItems.sumOf { it.price }

    // Créer la structure de l'écran avec un Column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Votre Panier", style = MaterialTheme.typography.headlineSmall)

        // Afficher les pizzas dans le panier avec un LazyColumn
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { pizza ->
                CartItem(pizza = pizza, onRemove = {
                    viewModel.removeFromCart(pizza) // Supprimer la pizza du panier
                })
            }
        }

        // Afficher le prix total et le bouton "Valider la commande" en bas
        Spacer(modifier = Modifier.height(16.dp))
        Text("Total : $totalPrice€", style = MaterialTheme.typography.titleLarge)

        // Bouton pour valider la commande
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Logique pour valider la commande (ajouter la commande à la base de données)
                val order = Order(orderDate = System.currentTimeMillis(), totalAmount = totalPrice, status = "En préparation")

                // Créer les items de commande (associer les pizzas avec la commande)
                val orderItems = cartItems.map { pizza ->
                    fr.unice.pizzaproject.model.OrderItem(
                        orderId = 0,
                        pizzaId = pizza.id,
                        quantity = 1
                    )  // Exemple : 1 pizza de chaque
                }

                // Passer la commande via OrderViewModel
                orderViewModel.placeOrder(order, orderItems)

                // Naviguer vers l'écran de confirmation
                navController.navigate("confirmation")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider la commande")
        }
        // Spacer pour l'espacement entre les boutons
        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour afficher l'historique des commandes
        Button(
            onClick = {
                // Naviguer vers l'historique des commandes
                navController.navigate("orderHistory")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voir l'historique des commandes")
        }
    }
}

@Composable
fun CartItem(pizza: Pizza, onRemove: () -> Unit) {
    // Affichage d'un élément de pizza dans le panier
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(
                    id = when (pizza.imageRes) {
                        "pizza1.png" -> R.drawable.pizza1
                        "pizza2.png" -> R.drawable.pizza2
                        "pizza3.png" -> R.drawable.pizza3
                        "pizza4.png" -> R.drawable.pizza4
                        "pizza5.png" -> R.drawable.pizza5
                        "pizza6.png" -> R.drawable.pizza6
                        "pizza7.png" -> R.drawable.pizza7
                        "pizza8.png" -> R.drawable.pizza8
                        "pizza9.png" -> R.drawable.pizza9
                        else -> R.drawable.pizza1 // Image par défaut
                    }
                ),
                contentDescription = pizza.name,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = pizza.name, style = MaterialTheme.typography.bodyLarge)
                pizza.ingredients.forEach {
                    Text(text = "- $it", style = MaterialTheme.typography.bodyMedium)
                }
                Text("Prix : ${pizza.price}€", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}


