package presentation

import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import domain.PResult
import domain.Repository
import domain.models.*
import kotlinx.coroutines.launch


val appRepository = Repository


fun main() {
    MainScreen()
}

class MainScreen : BaseScreen() {

    init {
        initView()
    }
    /**
     * Метод управляет всеми экранами приложения
     */
    override fun initView(): Unit = Window(title = "Администратор пицерии", size = IntSize(1400, 900)) {
        MaterialTheme {
            // common fields
            val titleScreenLiveData = remember { mutableStateOf<String>("Добро подаловать в приложение нашего пице-ресторана") }
            val errorLiveData = remember { mutableStateOf<String>("") }
            val loadingLiveData = remember { mutableStateOf<Boolean>(false) }

            // onboarding fields
            val loginLiveData = remember { mutableStateOf<String>("login1") }
            val passwordLiveData = remember { mutableStateOf<String>("password1") }
            val isInSystem = remember { mutableStateOf<Boolean>(false) }
            val worker = remember { mutableStateOf<Worker?>(null) }

            // create order
            val isOrderCreate = remember { mutableStateOf<Boolean>(false) }
            val pizzaItems = remember { mutableStateOf<List<PizzaItem>>(emptyList()) }
            val event = remember { mutableStateOf<Event>(Event.IDLE) }

            fun showLoading() {
                loadingLiveData.value = true
                errorLiveData.value = ""
                println("Show loading")
            }

            fun hideLoading() {
                loadingLiveData.value = false
                println("Hide loading")
            }

            fun showError(message: String) {
                println("Show error:${message}")
                errorLiveData.value = message
            }


            fun handledSuccessResult(result: PResult.Success<*>) {
                (result.data as? Worker)?.let {
                    println(it)
                    worker.value = it
                    isInSystem.value = true
                    event.value = Event.CreateOrder
                }
                (result.data as? List<Pizza>)?.let {
                    println(it)
                    titleScreenLiveData.value = "Соберите заказ"
                    pizzaItems.value = it.convertTo()
                }
            }


            //result handler
            fun handleResult(result: PResult<*>) {
                hideLoading()
                if (!result.isHandled) {
                    result.handle()
                    when (result) {
                        is PResult.Error -> showError(result.message)
                        is PResult.Success -> handledSuccessResult(result)
                        PResult.Empty -> Unit
                    }
                }
            }

            // Login button
            fun processSignIn(login: String, password: String) {
                scope.launch {
                    showLoading()
                    handleResult(appRepository.signIn(login, password))
                }
            }

            // Login button
            fun downloadMenu() {
                scope.launch {
                    showLoading()
                    handleResult(appRepository.getPizzaMenu())
                }
            }


            // Main window
            Column(Modifier.fillMaxWidth().fillMaxHeight().padding(top = 25.dp), Arrangement.spacedBy(50.dp)) {
                showTitleFiled(titleScreenLiveData)
                showDownloading(loadingLiveData)
                showErrorFiled(errorLiveData)

                loginScreen(
                    isInSystem = isInSystem,
                    loginLiveData = loginLiveData,
                    passwordLiveData = passwordLiveData,
                    processSignIn = { processSignIn(loginLiveData.value, passwordLiveData.value) }
                )


            }


            fun observeEvent(event: MutableState<Event>) {
                when (event.value) {
                    Event.SignIn -> {

                    }
                    Event.CreateOrder -> {
                        downloadMenu()
                    }
                }
            }

            observeEvent(event)


            @Composable
            fun observePizzas(pizzasItems: MutableState<List<PizzaItem>>) {
                createOrderScreen(
                    isInSystem = isInSystem,
                    isOrderCreate = isOrderCreate,
                    worker = worker,
                    pizzas = pizzaItems.value
                )
            }

            observePizzas(pizzasItems = pizzaItems)
        }
    }

}