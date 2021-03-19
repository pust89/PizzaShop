package presentation

import androidx.compose.animation.core.FloatDecayAnimationSpec
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.animation.FlingConfig
import androidx.compose.foundation.gestures.ScrollableController
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import domain.models.Pizza
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

//        ScrollableColumn { }(
//            Modifier.fillMaxWidth().fillMaxHeight().padding(top = 10.dp), Arrangement.spacedBy(50.dp)
//        ) {
        ScrollableColumn ( Modifier.fillMaxWidth().fillMaxHeight().padding(50.dp), isScrollEnabled = true){
                displayUserInfo(user)

                Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
//                    processSignIn()
                    }) {
                    Text("Create order")
                }
                pizzas.forEach {
                    val observablePizzaItem: MutableState<PizzaItem> = remember { mutableStateOf<PizzaItem>(it) }
                    tempList.add(observablePizzaItem)
                    addPizzaItemWithCounter(observablePizzaItem)
                }


            }
        }
    }


@Composable
fun displayUserInfo(user: MutableState<User?>) {
    Row(Modifier.fillMaxWidth().padding(top = 25.dp)) {
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
fun addPizzaItemWithCounter(pizzaItem: MutableState<PizzaItem>) {
    val pizza: PizzaItem = pizzaItem.value

    Row(Modifier.fillMaxWidth().padding(top = 25.dp), Arrangement.Center) {
        pizza.run {
            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = name,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Button(modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    count.let {
                        if (it > 0) {
                            count = it - 1
                        }
                    }
                }) {
                Text("Minus")
            }
            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = "Count = $count",
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Button(modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    count += 1
                }) {
                Text("Plus")
            }
            Text(
                fontSize = TextUnit.Companion.Sp(25),
                text = "Cost = ${count * price}",
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
fun ScrollableColumn(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(0f),
    horizontalGravity: Alignment.Horizontal = Alignment.Start,
    reverseScrollDirection: Boolean = false,
    isScrollEnabled: Boolean = true,
    children: @Composable ColumnScope.() -> Unit
){

}

