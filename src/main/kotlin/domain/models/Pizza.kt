package domain.models

/**
 * Local model
 */
data class Pizza(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int
)

fun Pizza.convertTo(): PizzaItem {
    return PizzaItem(
        id = this.id,
        image = this.imageUrl,
        name = this.name,
        price = this.price,
        count = 0,
        cost = 0
    )
}

fun List<Pizza>.convertTo(): List<PizzaItem> {
    return this.map {
        it.convertTo()
    }
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