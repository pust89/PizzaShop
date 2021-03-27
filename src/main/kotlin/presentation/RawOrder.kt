package presentation

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.models.PizzaItem
import domain.models.Worker


fun createRawOrder(
    worker: Worker,
    selectedPizzas: List<PizzaItem>
) {
    println(worker)
    selectedPizzas.forEach {
    println(it)
    }
}

/**
 * Отображает не подтвержденный заказ
 */
@Composable
fun createRawOrderColumn(
    pizzas: List<PizzaItem>,
    worker: MutableState<Worker?>,
    tempList: MutableList<MutableState<PizzaItem>>
) {
    Box(Modifier.width(600.dp).padding(top = 75.dp).background(Color.Red)) {
        ScrollableColumn(Modifier.fillMaxWidth().fillMaxHeight().padding(top = 10.dp), isScrollEnabled = true) {

//            pizzas.forEach {
//                val observablePizzaItem: MutableState<PizzaItem> = remember { mutableStateOf<PizzaItem>(it) }
//                tempList.add(observablePizzaItem)
//                addPizzaItemWithCounter(observablePizzaItem)
//            }

        }
    }
}