package domain.models

/**
 * Data model
 */
data class OrderDatabase(
    val orderId: Int = 0,
    val workerId: Int,
    val orderPizzas: String,
    val bill: Int,
    val date: String,
)

/**
 * Ui model
 */
data class OrderItem(
    val worker: Worker,
    val orderedPizzas: List<OrderedPizza>,
    val bill: Int,
    val date: String
) {
}

/**
 * Ui model
 */
data class OrderedPizza(
    val name: String,
    val quantity: Int,
    val cost: Int
) {
    override fun toString() = "$name qty:$quantity cost:$cost"
}


/**
 * Ui model
 */
data class DisplayOrderItem(
    val worker: Worker,
    val orderId: Int,
    val orderMenu: String,
    val bill: Int,
    val date: String,
)
