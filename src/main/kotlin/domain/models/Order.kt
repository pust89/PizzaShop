package domain.models

data class Order(
    val userId: Int,
    val pizzas: List<Pair<Int, Int>>,
    val coast: Int
)

