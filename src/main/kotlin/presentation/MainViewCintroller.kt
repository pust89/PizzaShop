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
import domain.models.*
import presentation.base.BaseViewController
import presentation.base.MyColors
import presentation.base.titleLiveData
import presentation.createorder.createOrderScreen
import presentation.createorder.isNeedCreateOrder
import presentation.createorder.pizzaItemsMenu
import presentation.login.*
import presentation.showorders.createShowOrderScreen
import presentation.showorders.displayedOrders
import presentation.showorders.isNeedShowOrders

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
                        processCreateNewOrder = { processCreateNewOrder() },
                    processShowOrders = {processShowOrders()})

                    customizeDownloadingDisplay()
                    customizeErrorDisplay()
                    customizeAdminDisplay()

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
                    createShowOrderScreen(isNeedShowOrders)

                }

            }
        }
    }


    override fun handledSuccessResult(result: PResult.Success<*>) {
        (result.data as? Admin)?.let {
            println("result =$it")
            currentAdmin.value = it
            isInSystem.value = true
            isNeedLogout.value = true
            downloadMenu()
        }

        (result.data as? ResponsePizza)?.let {
            pizzaItemsMenu.value = it.pizzas.convertToPizzaItem()
            isNeedCreateOrder.value = true
        }

        (result.data as? ResponseOrder)?.let {
            displayedOrders.value = it.orders
            isNeedShowOrders.value = true
            titleLiveData.value = "Заказы"
        }
    }


}