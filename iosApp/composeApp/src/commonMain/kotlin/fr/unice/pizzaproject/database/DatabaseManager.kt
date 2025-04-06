package fr.unice.pizzaproject.database



class DatabaseManager(databaseDriverFactory: DatabaseDriverFactory) {

    private val database: Database = Database(databaseDriverFactory.createDriver())

    val pizzaQueries: PizzaQueries = database.pizzaQueries
    val orderQueries: OrderQueries = database.orderQueries
    val orderItemQueries: OrderItemQueries = database.orderItemQueries

    /**
     * Transaction example:
     * Use this method if you need to execute multiple queries in a transaction
     */
    fun <T> transaction(block: () -> T): T {
        return database.transactionWithResult {
            block()
        }
    }
}
