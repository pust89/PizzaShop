package presentation.createorder

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import domain.models.*
import presentation.base.MyColors
import presentation.base.getCurrentTime
import presentation.login.currentAdmin
import kotlin.random.Random

val selectedPizzaItems = mutableStateOf<List<PizzaItem>>(emptyList())
val isNeedConfirmOrder = mutableStateOf<Boolean>(false)


/**
 * Отображает не подтвержденный заказ
 */
@Composable
fun createConfirmOrderScreen(
    saveNewOrder: (orderItem: OrderItem) -> Unit
) {
    if (isNeedConfirmOrder.value) {
        Box(Modifier.width(600.dp).fillMaxHeight().background(MyColors.MAIN_BACKGROUND)) {
            ScrollableColumn(
                Modifier.fillMaxWidth().fillMaxHeight().padding(start = 50.dp, end = 50.dp),
                isScrollEnabled = true
            ) {
                val order = OrderItem(
                    orderedPizzas = selectedPizzaItems.value.convertToOrderedPizza(),
                    bill = selectedPizzaItems.value.calculateBill(),
                    date = getCurrentTime()
                )
                addOrderedPizzaColumnNames()
                order.orderedPizzas.forEach {
                    addRawOrderedPizza(it)
                }
                addRawOrderBill(order.bill)
                addRawOrderDate(order.date)

                Box(Modifier.fillMaxWidth().wrapContentHeight().background(MyColors.MAIN_BACKGROUND)) {
                    Button(modifier = Modifier.align(Alignment.CenterStart).padding(top = 25.dp),
                        onClick = {
                            isNeedConfirmOrder.value = false
                        }) {
                        Text("Отмена")
                    }

                    Button(modifier = Modifier.align(Alignment.CenterEnd).padding(top = 25.dp),
                        onClick = {
                            saveNewOrder(order)
                        }) {
                        Text("Подтвердить заказ")
                    }
                }
            }
        }
    }
}

@Composable
private fun addOrderedPizzaColumnNames() {

    Row(Modifier.width(500.dp).padding(bottom = 10.dp).background(Color.Gray), Arrangement.Center) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Имя",
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(MyColors.NAME_BACKGROUND),
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Кол-во",
            modifier = Modifier.align(Alignment.CenterVertically).width(100.dp).background(Color.Cyan),
            textAlign = TextAlign.Center,
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Стоимость",
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(Color.Green),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
private fun addRawOrderedPizza(orderedPizza: OrderedPizza) {

    Row(Modifier.width(500.dp).padding(bottom = 10.dp).background(Color.Gray), Arrangement.Center) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = orderedPizza.name,
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(MyColors.NAME_BACKGROUND),
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = orderedPizza.quantity.toString(),
            modifier = Modifier.align(Alignment.CenterVertically).width(100.dp).background(Color.Cyan),
            textAlign = TextAlign.Center,
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = orderedPizza.cost.toString(),
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(Color.Green),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
private fun addRawOrderBill(bill: Int) {

    Row(Modifier.width(500.dp).padding(bottom = 10.dp).background(Color.Gray), Arrangement.Center) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Итого:",
            modifier = Modifier.align(Alignment.CenterVertically).width(300.dp).background(MyColors.NAME_BACKGROUND),
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "$bill. Руб.",
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(Color.Green),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
private fun addRawOrderDate(date: String) {
    Row(Modifier.width(500.dp).padding(bottom = 10.dp).background(Color.Gray), Arrangement.Center) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Дата: $date",
            modifier = Modifier.align(Alignment.CenterVertically).width(500.dp).background(MyColors.NAME_BACKGROUND),
        )
    }
}
