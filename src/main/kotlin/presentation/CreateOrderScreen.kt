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
import domain.models.User

@Composable
fun createOrderScreen(
    isInSystem: MutableState<Boolean>,
    isOrderCreate: MutableState<Boolean>,
    user: MutableState<User?>,
    pizzas: List<PizzaItem>
) {
    if (!isOrderCreate.value && isInSystem.value) {
        val tempList = mutableListOf<MutableState<PizzaItem>>()

        Column(Modifier.width(800.dp).padding(top = 50.dp).background(Color.Magenta)) {
            displayUserInfo(user)
            createOrderColumnsNames()
            ScrollableColumn(Modifier.fillMaxWidth().fillMaxHeight().padding(top = 10.dp), isScrollEnabled = true) {

//            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
//                onClick = {
////                    processSignIn()
//                }) {
//                Text("Create order")
//            }
                pizzas.forEach {
                    val observablePizzaItem: MutableState<PizzaItem> = remember { mutableStateOf<PizzaItem>(it) }
                    tempList.add(observablePizzaItem)
                    addPizzaItemWithCounter(observablePizzaItem)
                }

            }
        }
    }
}


@Composable
fun displayUserInfo(user: MutableState<User?>) {
    Row(Modifier.fillMaxWidth().padding(top = 10.dp)) {
        user.value?.run {
            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = "Your id:$id $firstName $secondName",
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
fun createOrderColumnsNames() {
    Row(Modifier.width(800.dp).padding(top = 10.dp).background(Color.Gray), Arrangement.Start) {
        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Name",
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp),
        )

        Text(
            fontSize = TextUnit.Companion.Sp(25),
            text = "Price",
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
            text = "Count",
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
            text = "Cost",
            modifier = Modifier.align(Alignment.CenterVertically).width(200.dp).background(Color.Green),
            textAlign = TextAlign.Center
        )

    }
}

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

