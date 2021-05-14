package domain.models

data class ResponseOrder(
    val orders: List<DisplayOrderItem>
)

/**
 * Data model
 */
data class SaveOrderDbModel(
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
    val orderId: Int,
    val worker: Worker,
    val orderPizzas: String,
    val bill: Int,
    val date: String,
    val checkUi: CheckUi
) {
    override fun toString() = "Официант:${worker.secondName} ${worker.firstName}\n\n" +
            "Заказ #$orderId\n\n$orderPizzas\n\nСчет:$bill руб.\n\n$date"
}
