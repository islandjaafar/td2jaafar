package fr.unice.pizzaproject.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*


import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Slider
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import fr.unice.pizzaproject.model.Pizza
import fr.unice.pizzaproject.viewmodel.PizzaViewModel

@Composable
fun PizzaDetailScreen(viewModel: PizzaViewModel, onAddToCart: (Pizza, Float) -> Unit, onBack: () -> Unit) {
    val selectedPizza by viewModel.selectedPizza.collectAsState()

    var extraCheese by remember { mutableStateOf(0f) }
    val basePrice = selectedPizza?.price ?: 0.0
    val extraCheesePrice = 1.0
    val finalPrice = basePrice + (extraCheese * extraCheesePrice)

    selectedPizza?.let { pizza ->
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image de la pizza

           LoadPizzaImage(pizza.imageRes)



            Spacer(modifier = Modifier.height(16.dp))

            // Nom de la pizza
            Text(
                text = pizza.name,
                style = MaterialTheme.typography.h1,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = "Prix de base: \$${"%.2f".format(basePrice)}",
                style = MaterialTheme.typography.h1,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sélection du supplément fromage
            Text("Ajoutez du fromage supplémentaire :")
            Slider(
                value = extraCheese,
                onValueChange = { extraCheese = it },
                valueRange = 0f..3f,
                steps = 2,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = "Fromage supplémentaire: ${extraCheese.toInt()}",
                style = MaterialTheme.typography.body1,
            )

            Text(
                text = "Prix total : \$${"%.2f".format(finalPrice)}",
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Boutons d'action
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    val pizzaWithExtraCheese = pizza.copy(price = finalPrice)
                    viewModel.addToCart(pizzaWithExtraCheese, extraCheese)
                    onAddToCart(pizza, extraCheese) // ✅ Ajout au panier

                }) {
                    Text("Ajouter au Panier")
                }

                OutlinedButton(onClick = onBack) {
                    Text("Retour")
                }
            }
        }
    } ?: run {
        Text("Aucune pizza sélectionnée", modifier = Modifier.padding(16.dp))
    }
}
@Composable
fun LoadPizzaImage(imageRes: String) {
    // Charger l'image différemment selon l'environnement
    val painter = remember {
        try {
            androidx.compose.ui.res.loadImageBitmap(java.io.File(imageRes).inputStream())
        } catch (e: Exception) {
            null
        }
    }

    if (painter != null) {
        Image(
            bitmap = painter,
            contentDescription = "Pizza Image",
            modifier = Modifier.fillMaxWidth().height(250.dp)
        )
    } else {
        Text("Image non trouvée")
    }
}

