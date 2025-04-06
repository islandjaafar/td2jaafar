package fr.unica.miage.donati.pizzaapp.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fr.unice.pizzaproject.R
import fr.unice.pizzaproject.model.Pizza


@Composable
fun PizzaCard(pizza: Pizza, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick), // Lors du clic, déclenche la fonction onClick

    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth() // Prendre toute la largeur disponible
        ) {
            // Image de la pizza
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
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )


            Spacer(modifier = Modifier.height(8.dp)) // Espace entre l'image et le texte

            // Nom de la pizza
            Text(
                text = pizza.name,
                style = MaterialTheme.typography.displaySmall,
                maxLines = 1, // Limite le texte à une ligne
                overflow = TextOverflow.Ellipsis // Si le texte est trop long, il sera tronqué
            )

            Spacer(modifier = Modifier.height(4.dp)) // Espace entre le nom et le prix

            // Affichage du prix de la pizza
            Text(
                text = "Prix: \$${"%.2f".format(pizza.price)}", // Formatage du prix
                style = MaterialTheme.typography.bodyMedium, // Style du texte
                maxLines = 1, // Limite à une seule ligne
                overflow = TextOverflow.Ellipsis // Tronque si le texte est trop long
            )
        }
    }
}
