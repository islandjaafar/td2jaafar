package fr.unice.pizzaproject.data

import fr.unice.pizzaproject.model.Pizza
import fr.unice.pizzaproject.R  // Import Android resources
//
//actual class DataSource {
//    actual fun loadPizzas(): List<Pizza> {
//        return listOf(
//            Pizza(1, "Margherita", R.drawable.pizza1, listOf("Tomato", "Mozzarella", "Basil"), 8.0),
//            Pizza(2, "Capricciosa", R.drawable.pizza2, listOf("Tomato", "Mozzarella", "Mushrooms", "Ham"), 10.0),
//            Pizza(3, "Diavola", R.drawable.pizza3, listOf("Tomato", "Mozzarella", "Pepperoni"), 9.0),
//            Pizza(4, "Quattro Stagioni", R.drawable.pizza4, listOf("Tomato", "Mozzarella", "Ham", "Mushrooms", "Olives"), 11.0),
//            Pizza(5, "Quattro Formaggi", R.drawable.pizza5, listOf("Mozzarella", "Gorgonzola", "Parmesan", "Fontina"), 12.0),
//            Pizza(6, "Marinara", R.drawable.pizza6, listOf("Tomato", "Garlic", "Oregano"), 7.0),
//            Pizza(7, "Pepperoni", R.drawable.pizza7, listOf("Tomato", "Mozzarella", "Pepperoni"), 9.0),
//            Pizza(8, "Prosciutto", R.drawable.pizza8, listOf("Tomato", "Mozzarella", "Ham"), 10.0),
//            Pizza(9, "Frutti di Mare", R.drawable.pizza9, listOf("Tomato", "Mozzarella", "Seafood", "Garlic"), 13.0)
//        )
//    }
//}

actual class DataSource {
    actual fun loadPizzas(): List<Pizza> {
        return listOf(
            Pizza(1, "Margherita","pizza1.png", listOf("Tomato", "Mozzarella", "Basil"), 8.0),
            Pizza(2, "Capricciosa",  "pizza2.png", listOf("Tomato", "Mozzarella", "Mushrooms", "Ham"), 10.0),
            Pizza(3, "Diavola",  "pizza3.png", listOf("Tomato", "Mozzarella", "Pepperoni"), 9.0),
            Pizza(4, "Quattro Stagioni",  "pizza4.png", listOf("Tomato", "Mozzarella", "Ham", "Mushrooms", "Olives"), 11.0),
            Pizza(5, "Quattro Formaggi",  "pizza5.png", listOf("Mozzarella", "Gorgonzola", "Parmesan", "Fontina"), 12.0),
            Pizza(6, "Marinara",  "pizza6.png", listOf("Tomato", "Garlic", "Oregano"), 7.0),
            Pizza(7, "Pepperoni",  "pizza7.png", listOf("Tomato", "Mozzarella", "Pepperoni"), 9.0),
            Pizza(8, "Prosciutto",  "pizza8.png", listOf("Tomato", "Mozzarella", "Ham"), 10.0),
            Pizza(9, "Frutti di Mare",  "pizza9.png", listOf("Tomato", "Mozzarella", "Seafood", "Garlic"), 13.0)
        )
    }

    actual fun getImagePath(imageName: String): Int {
        return when (imageName) {
            "pizza1" -> R.drawable.pizza1
            "pizza2" -> R.drawable.pizza2
            "pizza3" -> R.drawable.pizza3
            "pizza4" -> R.drawable.pizza4
            "pizza5" -> R.drawable.pizza5
            "pizza6" -> R.drawable.pizza6
            "pizza7" -> R.drawable.pizza7
            "pizza8" -> R.drawable.pizza8
            "pizza9" -> R.drawable.pizza9
            else -> R.drawable.pizza1 // Image par défaut
        }
    }
}
