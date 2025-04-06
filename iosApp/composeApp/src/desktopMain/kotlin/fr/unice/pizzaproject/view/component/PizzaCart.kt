package fr.unice.pizzaproject.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import fr.unice.pizzaproject.model.Pizza

@Composable
fun PizzaCard(pizza: Pizza, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick), // Clic sur la carte
        border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant) // Bordure pour bien visualiser
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Image de la pizza
            pizza.imageRes?.let { img ->
                Image(
                    bitmap = getDesktopImage(pizza.imageRes),  // Utilisation d'ImageBitmap pour Desktop
                    contentDescription = pizza.name,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)) // Coins arrondis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nom de la pizza
            Text(
                text = pizza.name,
                style = MaterialTheme.typography.h3,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Affichage du prix
            Text(
                text = "Prix: \$${"%.2f".format(pizza.price)}",
                style = MaterialTheme.typography.h2,
                maxLines = 1
            )
        }
    }
}
@Composable
fun getDesktopImage(imageName: String): ImageBitmap {
    return useResource("$imageName.png") { loadImageBitmap(it) } // ✅ Charge depuis `resources`
}
