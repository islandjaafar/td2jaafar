package fr.unice.pizzaproject.view.screen

import fr.unice.pizzaproject.viewmodel.PizzaViewModel


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.unice.pizzaproject.view.component.PizzaCard

@Composable
fun PizzaListScreen(viewModel: PizzaViewModel, onPizzaSelected: () -> Unit) {
    // Charger les pizzas au lancement
    LaunchedEffect(Unit) {
        viewModel.loadPizzas()
    }

    val pizzaList by viewModel.pizzaList.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Menu des Pizzas", style = MaterialTheme.typography.h1)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(pizzaList, key = { it.id }) { pizza ->
                PizzaCard(pizza = pizza) {
                    viewModel.selectPizza(pizza)
                    onPizzaSelected()
                }
            }
        }
    }
}
