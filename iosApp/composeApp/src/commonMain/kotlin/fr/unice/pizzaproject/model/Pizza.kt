package fr.unice.pizzaproject.model


data class Pizza(
    val id: Int = 0,  // Générer l'ID automatiquement
    val name: String,
    val imageRes: String,  // Référence à l'image
    val ingredients: List<String>,  // Liste des ingrédients
    val price: Double  // Prix de la pizza
)
