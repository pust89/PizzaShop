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
import domain.models.Pizza
import domain.models.Worker
import domain.models.convertTo
import presentation.base.MainPresenter
import presentation.base.MyColors
import presentation.createorder.createOrderScreen
import presentation.createorder.isNeedCreateOrder
import presentation.createorder.pizzaItemsMenu
import presentation.login.*

fun main() {
    MainScreen()
}

class MainScreen : MainPresenter() {

    init {
        initScreen()
    }

    /**
     * Метод управляет всеми экранами приложения
     */
    override fun initScreen() {
        Window(title = "Администратор пицерии", size = IntSize(1400, 900)) {
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
                            worker = worker,
                            pizzas = pizzaItemsMenu.value
                        )


                    }

                }

            }
        }
    }


    override fun handledSuccessResult(result: PResult.Success<*>) {
        (result.data as? Worker)?.let {
            println("result =$it")
            worker.value = it
            isInSystem.value = true
            isNeedLogout.value = true
            downloadMenu()
        }

        (result.data as? List<Pizza>)?.let {
            pizzaItemsMenu.value = it.convertTo()
            isNeedCreateOrder.value = true
        }
    }


}