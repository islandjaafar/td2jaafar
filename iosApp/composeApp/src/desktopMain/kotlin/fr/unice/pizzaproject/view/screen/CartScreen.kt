package fr.unice.pizzaproject.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.jetbrains.skia.Image
import java.io.File

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import fr.unice.pizzaproject.model.Order
import fr.unice.pizzaproject.model.OrderItem
import fr.unice.pizzaproject.model.Pizza
import fr.unice.pizzaproject.viewmodel.OrderViewModel
import fr.unice.pizzaproject.viewmodel.PizzaViewModel



@Composable
fun CartScreen(viewModel: PizzaViewModel, orderViewModel: OrderViewModel, onConfirmOrder: () -> Unit, onViewHistory: () -> Unit) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice = cartItems.sumOf { it.price }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Votre Panier", style = MaterialTheme.typography.h2)

        // Liste des pizzas dans le panier
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            items(cartItems) { pizza ->
                CartItem(pizza, onRemove = { viewModel.removeFromCart(pizza) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Total : $totalPrice€", style = MaterialTheme.typography.h1)

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val order = Order(orderDate = System.currentTimeMillis(), totalAmount = totalPrice, status = "En préparation")

                val orderItems = cartItems.map { pizza ->
                    OrderItem(orderId = 0, pizzaId = pizza.id, quantity = 1)
                }

                orderViewModel.placeOrder(order, orderItems)
                onConfirmOrder() // Callback pour naviguer vers confirmation
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Valider la commande")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onViewHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voir l'historique des commandes")
        }
    }
}

@Composable
fun CartItem(pizza: Pizza, onRemove: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),

    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {

            pizza.imageRes?.let { img ->
                val imageBitmap = loadImageBitmap(img)
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = pizza.name,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = pizza.name, style = MaterialTheme.typography.body1)
                pizza.ingredients.forEach {
                    Text(text = "- $it", style = MaterialTheme.typography.body2)
                }
                Text("Prix : ${pizza.price}€", style = MaterialTheme.typography.body2)
            }

            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}

fun loadImageBitmap(imagePath: String): ImageBitmap? {
    return try {
        val file = File(imagePath)

        if (!file.exists()) {
            println("Erreur: L'image $imagePath n'existe pas.")
            return null
        }

        val imageBytes = file.readBytes()
        val skiaImage = Image.makeFromEncoded(imageBytes)

        skiaImage.asImageBitmap() // ✅ Convertit correctement l’image Skia en ImageBitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null // En cas d'erreur, retourne null et évite un crash
    }
}

