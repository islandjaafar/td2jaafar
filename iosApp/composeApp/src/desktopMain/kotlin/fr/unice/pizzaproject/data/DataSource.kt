package fr.unice.pizzaproject.data


import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import fr.unice.pizzaproject.model.Pizza

actual class DataSource {
    actual fun loadPizzas(): List<Pizza> {
        return listOf(
            Pizza(1, "Margherita",  "pizza1", listOf("Tomato", "Mozzarella", "Basil"), 8.0),
            Pizza(2, "Capricciosa",  "pizza2", listOf("Tomato", "Mozzarella", "Mushrooms", "Ham"), 10.0),
            Pizza(3, "Diavola",  "pizza3", listOf("Tomato", "Mozzarella", "Pepperoni"), 9.0),
            Pizza(4, "Quattro Stagioni",  "pizza4", listOf("Tomato", "Mozzarella", "Ham", "Mushrooms", "Olives"), 11.0),
            Pizza(5, "Quattro Formaggi",  "pizza5", listOf("Mozzarella", "Gorgonzola", "Parmesan", "Fontina"), 12.0),
            Pizza(6, "Marinara",  "pizza6", listOf("Tomato", "Garlic", "Oregano"), 7.0),
            Pizza(7, "Pepperoni",  "pizza7", listOf("Tomato", "Mozzarella", "Pepperoni"), 9.0),
            Pizza(8, "Prosciutto",  "pizza8", listOf("Tomato", "Mozzarella", "Ham"), 10.0),
            Pizza(9, "Frutti di Mare",  "pizza9", listOf("Tomato", "Mozzarella", "Seafood", "Garlic"), 13.0)
        )
    }

    private fun loadImage(resourcePath: String): ImageBitmap {
        return useResource(resourcePath) { loadImageBitmap(it) }
    }

    actual fun getImagePath(imageName: String): Int {
        TODO("Not yet implemented")
    }
}
