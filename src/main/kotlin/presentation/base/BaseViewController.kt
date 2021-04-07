package presentation.base

import androidx.compose.runtime.mutableStateOf
import domain.PResult
import domain.Repository
import domain.models.OrderItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.createorder.isNeedCreateOrder
import presentation.createorder.isNeedConfirmOrder
import presentation.createorder.selectedPizzaItems
import presentation.login.*

val titleLiveData = mutableStateOf<String>("Добро подаловать в приложение нашего пице-ресторана")
val errorLiveData = mutableStateOf<String>("")
val loadingLiveData = mutableStateOf<Boolean>(false)


abstract class BaseViewController {

    val appRepository = Repository

    private val coroutineJob = Job()
    val scope = CoroutineScope(coroutineJob)

    abstract fun initScreen()

    abstract fun handledSuccessResult(result: PResult.Success<*>)


    open fun destroy() {
        coroutineJob.cancel()
    }

    //result handler
    open fun handleResult(result: PResult<*>) {
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

    open fun showLoading() {
        loadingLiveData.value = true
        errorLiveData.value = ""
        println("Show loading")
    }

    open fun hideLoading() {
        loadingLiveData.value = false
        println("Hide loading")
    }

    open fun showError(message: String) {
        println("Show error:${message}")
        errorLiveData.value = message
    }

    // Login button
    fun processSignIn(login: String, password: String) {
        scope.launch {
            showLoading()
            handleResult(appRepository.signIn(login, password))
        }
    }

    // Login button
    fun processSignOut() {
        scope.launch {
            showLoading()
            delay(1000)
            loginLiveData.value = ""
            passwordLiveData.value = ""
            isNeedConfirmOrder.value = false
            isInSystem.value = false
            isNeedLogout.value = false
            currentWorker.value = null
            titleLiveData.value = "Добро подаловать в приложение нашего пице-ресторана"
            hideLoading()
        }
    }


    fun processCreateNewOrder() {
        scope.launch {
            isNeedCreateOrder.value = false
            isNeedConfirmOrder.value = false
            selectedPizzaItems.value = emptyList()
            showLoading()
            delay(1000)
            downloadMenu()
        }
    }

    fun saveNewOrder(orderItem: OrderItem) {
        println("Save newOrderInvoked")
        scope.launch {
            showLoading()
            delay(1000)
            isNeedCreateOrder.value = false
            isNeedConfirmOrder.value = false
            selectedPizzaItems.value = emptyList()
            handleResult(appRepository.saveNewOrder(orderItem))
        }
    }

    fun downloadMenu() {
        scope.launch {
            showLoading()
            handleResult(appRepository.getPizzaMenu())
        }
    }
}