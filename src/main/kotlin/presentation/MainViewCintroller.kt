package presentation

import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import domain.PResult
import domain.models.PizzaDatabase
import domain.models.Worker
import domain.models.convertToPizzaItem
import presentation.base.BaseViewController
import presentation.base.MyColors
import presentation.createorder.createOrderScreen
import presentation.createorder.isNeedCreateOrder
import presentation.createorder.pizzaItemsMenu
import presentation.login.*

fun main() {
    MainViewController()
}

class MainViewController : BaseViewController() {

    init {
        initScreen()
    }

    /**
     * Метод управляет всеми экранами приложения
     */
    override fun initScreen() {
        Window(title = "Администратор пицерии", size = IntSize(1450, 900)) {
            MaterialTheme {
                Column(Modifier.fillMaxWidth().fillMaxHeight().background(MyColors.MAIN_BACKGROUND)) {
                    customizeTitleDisplay(
                        processSignOut = { processSignOut() },
                        processCreateNewOrder = { processCreateNewOrder() })

                    customizeDownloadingDisplay()
                    customizeErrorDisplay()
                    customizeWorkerDisplay()

                    loginScreen(
                        processSignIn = { processSignIn(loginLiveData.value, passwordLiveData.value) }
                    )

                    if (isInSystem.value) {
                        createOrderScreen(
                            isNeedCreateOrder = isNeedCreateOrder,
                            pizzas = pizzaItemsMenu.value,
                            saveNewOrder = { newOrderItem ->
                                saveNewOrder(newOrderItem)
                            }
                        )

                    }

                }

            }
        }
    }


    override fun handledSuccessResult(result: PResult.Success<*>) {
        (result.data as? Worker)?.let {
            println("result =$it")
            currentWorker.value = it
            isInSystem.value = true
            isNeedLogout.value = true
            downloadMenu()
        }

        (result.data as? List<PizzaDatabase>)?.let {
            pizzaItemsMenu.value = it.convertToPizzaItem()
            isNeedCreateOrder.value = true
        }
    }


}