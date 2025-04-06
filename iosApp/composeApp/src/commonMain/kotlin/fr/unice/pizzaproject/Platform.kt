package fr.unice.pizzaproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform