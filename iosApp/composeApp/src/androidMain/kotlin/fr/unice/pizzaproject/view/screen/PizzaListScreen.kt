package fr.unica.miage.donati.pizzaapp.view.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import fr.unica.miage.donati.pizzaapp.view.component.PizzaCard
import fr.unica.miage.donati.pizzaapp.viewmodel.PizzaViewModel

@Composable
fun PizzaListScreen(viewModel: PizzaViewModel, navController: NavController) {
    // Charger les pizzas lorsque l'écran est affiché
    LaunchedEffect(Unit) {
        viewModel.loadPizzas()
    }

    // Observer la liste des pizzas dans le ViewModel
    val pizzaList by viewModel.pizzaList.collectAsState()

    LazyColumn {
        // Utiliser 'items' correctement avec une clé unique (ici 'id' de la pizza)
        items(pizzaList, key = { pizza -> pizza.id }) { pizza ->
            // Appel du composant PizzaCard pour chaque pizza
            PizzaCard(pizza = pizza) {
                // Lors du clic sur la carte de pizza, sélectionnez la pizza et naviguez vers l'écran de détails
                viewModel.selectPizza(pizza)
                navController.navigate("pizzaDetail") // Naviguer vers l'écran des détails
            }
        }
    }
}
