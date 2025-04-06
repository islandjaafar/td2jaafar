package fr.unica.miage.donati.pizzaapp.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import fr.unica.miage.donati.pizzaapp.viewmodel.PizzaViewModel
import fr.unice.pizzaproject.R

@Composable
fun PizzaDetailScreen(viewModel: PizzaViewModel, navController: NavController) {
    val selectedPizza by viewModel.selectedPizza.collectAsState()

    // Variable pour gérer la quantité de fromage supplémentaire
    var extraCheese by remember { mutableStateOf(0f) }
    val basePrice = selectedPizza?.price ?: 0.0
    val extraCheesePrice = 1.0 // Prix du supplément par unité de fromage
    val finalPrice = basePrice + (extraCheese * extraCheesePrice)

    selectedPizza?.let { pizza ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Affichage de l'image de la pizza
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp)
            )

            // Nom de la pizza
            Text(
                text = pizza.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )

            // Affichage du prix de base
            Text(
                text = "Prix de base: \$${"%.2f".format(basePrice)}",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Affichage du Slider pour extra cheese
            Text("Ajoutez du fromage supplémentaire :")
            Slider(
                value = extraCheese,
                onValueChange = { extraCheese = it },
                valueRange = 0f..3f, // Valeur entre 0 et 3
                steps = 2, // Cela permet d'avoir 0, 1, 2, 3 comme valeurs possibles
                modifier = Modifier.fillMaxWidth(),
            )

            // Affichage du supplément
            Text(
                text = "Fromage supplémentaire: ${extraCheese.toInt()}",
                style = MaterialTheme.typography.bodyMedium,
            )

            // Affichage du prix final
            Text(
                text = "Prix total : \$${"%.2f".format(finalPrice)}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bouton pour ajouter la pizza au panier
            Button(
                onClick = {
                    // Ajouter la pizza avec supplément au panier
                    val pizzaWithExtraCheese = pizza.copy(price = finalPrice) // Vous pouvez mettre à jour l'objet Pizza si nécessaire
                    viewModel.addToCart(pizzaWithExtraCheese, extraCheese)
                    navController.navigate("cart") // Naviguer vers le panier
                }
            ) {
                Text("Ajouter au Panier")
            }
        }
    } ?: run {
        // Si aucune pizza n'est sélectionnée, afficher un message d'erreur
        Text("Aucune pizza sélectionnée")
    }
}

