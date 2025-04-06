package fr.unice.pizzaproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart



import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import fr.unice.pizzaproject.database.DatabaseDriverFactory
import fr.unice.pizzaproject.database.DatabaseManager
import fr.unice.pizzaproject.repository.OrderRepository
import fr.unice.pizzaproject.repository.PizzaRepository
import fr.unice.pizzaproject.viewmodel.OrderViewModel
import fr.unice.pizzaproject.viewmodel.PizzaViewModel
import fr.unice.pizzaproject.view.screen.PizzaListScreen
import fr.unice.pizzaproject.view.screen.PizzaDetailScreen
import fr.unice.pizzaproject.view.screen.CartScreen
import fr.unice.pizzaproject.view.screen.OrderHistoryScreen

@Composable
fun AppDesktop() {
    // Instancier la base de données
    val databaseManager = remember { DatabaseManager(DatabaseDriverFactory()) }

    // Instancier les repositories
    val pizzaRepository = remember { PizzaRepository(databaseManager.pizzaQueries) }
    val orderRepository = remember { OrderRepository(databaseManager) }

    // Instancier les ViewModels
    val pizzaViewModel = remember { PizzaViewModel(pizzaRepository) }
    val orderViewModel = remember { OrderViewModel(orderRepository) }

    // État pour gérer la navigation (pas de NavController en Desktop)
    var currentScreen by remember { mutableStateOf("pizzaList") }

    // Charger les pizzas au lancement
    LaunchedEffect(Unit) {
        pizzaViewModel.loadPizzas()
    }

    MaterialTheme {
        Scaffold(
            topBar = { TopAppBarView { currentScreen = it } },
            bottomBar = { NavigationBarView { currentScreen = it } }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (currentScreen) {
                    "pizzaList" -> PizzaListScreen(pizzaViewModel) { currentScreen = "pizzaDetail" }
                    "pizzaDetail" -> PizzaDetailScreen(
                        pizzaViewModel,
                        onAddToCart = { pizza, extraCheese ->
                            pizzaViewModel.addToCart(pizza, extraCheese)
                            currentScreen = "cart"
                        }
                    ) { currentScreen = "cart" }

                    "cart" -> CartScreen(
                        viewModel = pizzaViewModel,
                        orderViewModel = orderViewModel,
                        onConfirmOrder = { currentScreen = "orderHistory" },
                        onViewHistory = { currentScreen = "orderHistory" }
                    )


                    "orderHistory" -> OrderHistoryScreen(orderViewModel) { currentScreen = "pizzaList" }
                }
            }
        }
    }
}

@Composable
fun TopAppBarView(onNavigate: (String) -> Unit) {
    TopAppBar(
        title = { Text("PizzaApp - Desktop") },
        actions = {
            IconButton(onClick = { onNavigate("pizzaList") }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = { onNavigate("cart") }) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NavigationBarView(onNavigate: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly // Espacement entre les boutons
    ) {
        IconButton(onClick = { onNavigate("pizzaList") }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
                Text("Home", style = MaterialTheme.typography.body2)
            }
        }

        IconButton(onClick = { onNavigate("cart") }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                Text("Cart", style = MaterialTheme.typography.body2)
            }
        }
    }
}

// Fonction principale pour lancer l’application Desktop
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "PizzaApp Desktop") {
        AppDesktop()
    }
}

