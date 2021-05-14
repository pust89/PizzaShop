package presentation.showorders

import androidx.compose.desktop.Window
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.maxLinesHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import domain.models.DisplayOrderItem
import presentation.base.MyColors
import presentation.base.titleLiveData

val displayedOrders = mutableStateOf<List<DisplayOrderItem>>(emptyList())
val isNeedShowOrders = mutableStateOf<Boolean>(false)

/**
 * Агрегирует методы по созданию экрана "Показать заказы"
 */
@Composable
fun createShowOrderScreen(
    isNeedShowOrders: MutableState<Boolean>
) {
    if (isNeedShowOrders.value) {
        titleLiveData.value = "Список заказов"

        Box(Modifier.fillMaxWidth().fillMaxHeight().background(MyColors.MAIN_BACKGROUND)) {
            ScrollableColumn(
                Modifier.fillMaxWidth().fillMaxHeight().padding(start = 50.dp, end = 50.dp),
                isScrollEnabled = true
            ) {
                addOrdersColumnNames()
                displayedOrders.value.forEach {
                    addOrderRaw(it)
                }

            }
        }
    }

}

@Composable
private fun addOrderRaw(item: DisplayOrderItem) {
    val isShowInfo = remember { mutableStateOf(false) }
    Row(Modifier.fillMaxWidth().padding(bottom = 10.dp).background(Color.LightGray), Arrangement.Center) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "${item.worker.secondName} ${item.worker.firstName.subSequence(0,1)}",
            modifier = Modifier.align(Alignment.CenterVertically).width(300.dp).fillMaxHeight().background(Color.Cyan),
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = item.bill.toString(),
            modifier = Modifier.align(Alignment.CenterVertically).width(250.dp).fillMaxHeight().background(Color.Green),
            textAlign = TextAlign.Center
        )
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = item.date,
            modifier = Modifier.align(Alignment.CenterVertically).width(400.dp).fillMaxHeight().background(Color.Cyan),
            textAlign = TextAlign.Center,
        )

        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = {
                isShowInfo.value = true
            }) {
            Text("Check")
        }

        if (isShowInfo.value) {
            Window(title = "Подробности заказа", size = IntSize(400, 640),onDismissRequest = {
                isShowInfo.value=false
            }) {
                Text(
            fontSize = TextUnit.Companion.Sp(20),
            text = item.checkUi.toString(),
            modifier = Modifier.align(Alignment.CenterVertically).width(400.dp).fillMaxHeight().background(MyColors.NAME_BACKGROUND),
            textAlign = TextAlign.Center,
        )
            }
        }

    }
}

@Composable
private fun addOrdersColumnNames() {

    Row(Modifier.fillMaxWidth().padding(bottom = 10.dp).background(Color.LightGray), Arrangement.Center) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Оффициант",
            modifier = Modifier.align(Alignment.CenterVertically).width(300.dp).background(Color.Cyan),
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Стоимость",
            modifier = Modifier.align(Alignment.CenterVertically).width(250.dp).background(Color.Green),
            textAlign = TextAlign.Center
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Дата",
            modifier = Modifier.align(Alignment.CenterVertically).width(400.dp).background(Color.Cyan),
            textAlign = TextAlign.Center,
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "",
            modifier = Modifier.align(Alignment.CenterVertically).width(60.dp).background(Color.LightGray),
            textAlign = TextAlign.Center,
        )
    }
}

