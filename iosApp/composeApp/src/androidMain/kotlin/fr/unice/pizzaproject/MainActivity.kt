package fr.unice.pizzaproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.unica.miage.donati.pizzaapp.view.screen.CartScreen
import fr.unice.pizzaproject.view.screen.OrderHistoryScreen
import fr.unica.miage.donati.pizzaapp.view.screen.PizzaDetailScreen
import fr.unica.miage.donati.pizzaapp.view.screen.PizzaListScreen
import fr.unica.miage.donati.pizzaapp.viewmodel.OrderViewModel
import fr.unica.miage.donati.pizzaapp.viewmodel.PizzaViewModel
import fr.unice.pizzaproject.database.DatabaseDriverFactory
import fr.unice.pizzaproject.database.DatabaseManager
import fr.unice.pizzaproject.repository.OrderRepository
import fr.unice.pizzaproject.repository.PizzaRepository

import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val databaseManager by lazy {
        DatabaseManager(DatabaseDriverFactory(this))
    }

    // Création des repositories
    private val pizzaRepository by lazy { PizzaRepository(databaseManager.pizzaQueries) }
    private val orderRepository by lazy { OrderRepository(databaseManager) }

    // Création des ViewModels (sans ViewModelProvider)
    private val pizzaViewModel by lazy { PizzaViewModel(pizzaRepository) }
    private val orderViewModel by lazy { OrderViewModel(orderRepository) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Pass both pizzaViewModel and orderViewModel to the navigation host
                AppNavHost(
                    viewModel = pizzaViewModel,
                    orderViewModel = orderViewModel
                )
            }
        }
        // Utiliser lifecycleScope pour lancer la coroutine
        lifecycleScope.launch {
            // Appeler la fonction suspend pour charger les pizzas
            pizzaViewModel.loadPizzas()  // Cette fonction est suspendue, donc elle doit être dans une coroutine
        }
    }
}

//@Composable
//fun AppNavHost(viewModel: PizzaViewModel, orderViewModel: OrderViewModel) {
//    val navController = rememberNavController()
//
//    // Scaffold to set up the bottom navigation
//    Scaffold(
//        topBar = { TopAppBarView(navController) }, // TopAppBar
//        bottomBar = { NavigationBarView(navController) } // BottomNavigationBar
//    ) { paddingValues ->
//        // Use paddingValues to ensure the content is not hidden under the bars
//        NavHost(navController = navController, startDestination = "splashScreen", modifier = Modifier.padding(paddingValues)) {
//            composable("splashScreen") {
//                SplashScreen(navController = navController)
//            }
//            composable("pizzaList") {
//                PizzaListScreen(viewModel = viewModel, navController = navController)
//            }
//            composable("pizzaDetail") {
//                PizzaDetailScreen(viewModel = viewModel, navController = navController)
//            }
//            composable("cart") {
//                CartScreen(viewModel = viewModel, orderViewModel = orderViewModel, navController = navController)
//            }
//            // Ajouter une destination pour "confirmation" ici
//            composable("confirmation") {
//                ConfirmationScreen(navController = navController)
//            }
//            composable("orderHistory") {
//                OrderHistoryScreen(orderViewModel = orderViewModel, navController = navController)
//            }
//        }
//    }
//}
@Composable
fun AppNavHost(viewModel: PizzaViewModel, orderViewModel: OrderViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBarView(navController) },
        bottomBar = { NavigationBarView(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "pizzaList",
            modifier = Modifier.padding(paddingValues)
        ) {
//            composable("splashScreen") {
//                SplashScreen(navController = navController)
//            }
            composable("pizzaList") { PizzaListScreen(viewModel, navController) }
            composable("pizzaDetail") { PizzaDetailScreen(viewModel, navController) }
            composable("cart") { CartScreen(viewModel, orderViewModel, navController) }
            composable("confirmation") { ConfirmationScreen(navController) }
            composable("orderHistory") { OrderHistoryScreen(orderViewModel, navController) }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(navController: androidx.navigation.NavController) {
    TopAppBar(
        title = { Text("PizzaApp") },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NavigationBarView(navController: androidx.navigation.NavController) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = {
                navController.navigate("pizzaList")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = false,
            onClick = {
                navController.navigate("cart")
            }
        )
    }
}

// ViewModel Factory for PizzaViewModel
class PizzaViewModelFactory(private val pizzaRepository: PizzaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PizzaViewModel(pizzaRepository) as T
    }
}

// ViewModel Factory for OrderViewModel
class OrderViewModelFactory(private val orderRepository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderViewModel(orderRepository) as T
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val navController = rememberNavController()
    //AppNavHost(viewModel = PizzaViewModel(pizzaRepository), orderViewModel = OrderViewModel())
}
@Composable
fun ConfirmationScreen(navController: NavController) {
    // Afficher un message de confirmation ou toute autre information nécessaire
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Votre commande a été validée !", style = MaterialTheme.typography.h1)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("pizzaList") } // Retourner à l'écran de la liste des pizzas
        ) {
            Text("Retour à l'accueil")
        }
    }
}
