package domain.models

data class ResponsePizza(
    val pizzas: List<PizzaDatabase>
)

/**
 * Data model
 */
data class PizzaDatabase(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int
)


fun List<PizzaDatabase>.convertToPizzaItem(): List<PizzaItem> {
    return this.map {
        PizzaItem(
            id = it.id,
            image = it.imageUrl,
            name = it.name,
            price = it.price,
            count = 0,
            cost = 0
        )
    }
}

fun List<PizzaItem>.calculateBill(): Int {
    return if (isNotEmpty()) {
        var temp = 0
        forEach {
            temp += it.cost
        }
        temp
    } else 0
}

fun List<PizzaItem>.convertToOrderedPizza(): List<OrderedPizza> {
    return map {
        OrderedPizza(
            name = it.name,
            quantity = it.count,
            cost = it.cost
        )
    }
}

fun List<OrderedPizza>.toStringValue(): String {
    return if (isNotEmpty()) {
        var temp = ""
        forEach {
            temp += "$it\n"
        }
        temp
    } else ""
}

/**
 * Ui model
 */
data class PizzaItem(
    val id: Int,
    val image: String,
    val name: String,
    val price: Int,
    var count: Int,
    var cost: Int
)