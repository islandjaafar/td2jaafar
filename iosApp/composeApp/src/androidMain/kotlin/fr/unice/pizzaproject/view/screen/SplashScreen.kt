package fr.unica.miage.donati.pizzaapp.view.screen

import android.os.SystemClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fr.unice.pizzaproject.R

import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Utiliser LaunchedEffect pour gérer le délai de 5 secondes
    LaunchedEffect(Unit) {
        delay(5000) // Délai de 5 secondes (5000 ms)
        navController.navigate("pizzaList") // Naviguer vers la page suivante (par exemple, liste des pizzas)
    }

    // Affichage du logo pendant 5 secondes
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Affichez ici votre logo
        Image(
            painter = painterResource(id = R.drawable.logo), // Remplacez par votre logo
            contentDescription = "Logo",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}
