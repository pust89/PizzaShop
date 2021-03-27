package presentation

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import domain.models.PizzaItem
import domain.models.Worker

/**
 * Агрегирует методы по созданию экрана "Создания заказа"
 */
@Composable
fun createOrderScreen(
    isInSystem: MutableState<Boolean>,
    isOrderCreate: MutableState<Boolean>,
    worker: MutableState<Worker?>,
    pizzas: List<PizzaItem>
) {
    if (!isOrderCreate.value && isInSystem.value) {

        val mutablePizzas: MutableList<MutableState<PizzaItem>> = mutableListOf<MutableState<PizzaItem>>()

        displayWorkerInfo(worker)
        Row(Modifier.fillMaxWidth().fillMaxHeight()) {
            createMenuColumn(pizzas, worker, mutablePizzas)
            createRawOrderColumn(pizzas, worker, mutablePizzas)
        }

    }
}

/**
 * Создается колонка меню, где отображается залогиненный пользователь,
 * заголовки таблицы меню и само меню сос счетчиками.
 */
@Composable
fun createMenuColumn(
    pizzas: List<PizzaItem>,
    worker: MutableState<Worker?>,
    tempList: MutableList<MutableState<PizzaItem>>
) {
    Box(Modifier.width(800.dp).padding(top = 75.dp).background(Color.LightGray)) {

        createOrderColumnsNames()
        ScrollableColumn(Modifier.fillMaxWidth().fillMaxHeight().padding(top = 10.dp), isScrollEnabled = true) {

            pizzas.forEach {
                val observablePizzaItem: MutableState<PizzaItem> = remember { mutableStateOf<PizzaItem>(it) }
                tempList.add(observablePizzaItem)
                addPizzaItemWithCounter(observablePizzaItem)
            }

            Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 25.dp),
                onClick = {
                    createRawOrder(worker = worker.value!!,
                        selectedPizzas = tempList.map {
                            it.value
                        }.filter {
                            it.count > 0
                        })
                }) {
                Text("Создать заказ")
            }
        }
    }
}

/**
 * Метод добавляет отображение залогиненного пользователя
 */
@Composable
fun displayWorkerInfo(worker: MutableState<Worker?>) {
    Column(Modifier.fillMaxWidth().padding(top = 10.dp)) {
        worker.value?.run {
            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = "Работник: id=$id, $firstName $secondName",
            )
        }
    }
}


/**
 * Метод добавляет заголовки в отображаемую таблицу пицц.
 * Элемент содержит в себе:
 * название,
 * цена за 1 шт.,
 * кнопка + (увеличиваетколичество)
 * количество пицц,
 * кнопка - (уменьшает количество)
 * стоимость (количество * цена за 1 шт.)
 */
@Composable
fun createOrderColumnsNames() {

    Row(Modifier.width(800.dp).padding(top = 10.dp, bottom = 10.dp).background(Color.Gray), Arrangement.Start) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Имя",
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp),
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Цена",
            modifier = Modifier.align(Alignment.CenterVertically).width(100.dp).background(Color.Green),
            textAlign = TextAlign.Center,
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "",
            modifier = Modifier.align(Alignment.CenterVertically).width(100.dp)
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Кол-во",
            modifier = Modifier.align(Alignment.CenterVertically).width(100.dp).background(Color.Cyan),
            textAlign = TextAlign.Center,
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "",
            modifier = Modifier.align(Alignment.CenterVertically).width(100.dp)
        )
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Стоимость",
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(Color.Green),
            textAlign = TextAlign.Center
        )

    }
}

/**
 * Метод добавляет запись в отображаемую таблицу пицц.
 * Элемент содержит в себе:
 * название,
 * цена за 1 шт.,
 * кнопка + (увеличиваетколичество)
 * количество пицц,
 * кнопка - (уменьшает количество)
 * стоимость (количество * цена за 1 шт.)
 */
@Composable
fun addPizzaItemWithCounter(pizzaItem: MutableState<PizzaItem>) {
    val pizza: PizzaItem = pizzaItem.value
    val count = remember { mutableStateOf<Int>(pizza.count) }
    val cost = remember { mutableStateOf<Int>(0) }

    Row(Modifier.fillMaxWidth().padding(top = 25.dp), Arrangement.Start) {
        pizza.run {

            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = name,
                modifier = Modifier.align(Alignment.CenterVertically).width(200.dp),
            )

            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = price.toString(),
                modifier = Modifier.align(Alignment.CenterVertically).width(100.dp).background(Color.Green),
                textAlign = TextAlign.Center
            )

            Button(modifier = Modifier.align(Alignment.CenterVertically).width(100.dp),
                onClick = {
                    count.value.let {
                        if (it > 0) {
                            count.value = it - 1
                            cost.value = count.value * price
                            this.cost = cost.value
                            this.count = count.value
                        }
                    }
                }) {
                Text("-")
            }

            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = count.value.toString(),
                modifier = Modifier.align(Alignment.CenterVertically).width(100.dp).background(Color.Cyan),
                textAlign = TextAlign.Center
            )

            Button(modifier = Modifier.align(Alignment.CenterVertically).width(100.dp),
                onClick = {
                    count.value = count.value + 1
                    cost.value = count.value * price
                    this.cost = cost.value
                    this.count = count.value
                }) {
                Text("+")
            }

            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = cost.value.toString(),
                modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(Color.Green),
                textAlign = TextAlign.Center
            )
        }
    }
}

