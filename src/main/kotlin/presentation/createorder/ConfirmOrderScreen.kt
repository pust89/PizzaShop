package presentation.createorder

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.models.PizzaItem

val selectedPizzaItems = mutableStateOf<List<PizzaItem>>(emptyList())

/**
 * Отображает не подтвержденный заказ
 */
@Composable
fun createRawOrderColumn(populateState: MutableState<Boolean>) {
    Box(Modifier.width(600.dp).background(Color.Red)) {
        ScrollableColumn(Modifier.fillMaxWidth().fillMaxHeight().padding(top = 10.dp), isScrollEnabled = true) {
            if (populateState.value) {
                selectedPizzaItems.value.forEach {
                    println(it.name + it.count)
                }
            }

        }
    }
}
