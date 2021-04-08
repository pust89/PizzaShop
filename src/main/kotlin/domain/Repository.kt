package domain

import data.DatabaseInteractor
import domain.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * Репозиторий (единственно-верный источник данных в приложении)
 */
object Repository {

    private val databaseInteractor: DatabaseInteractor = DatabaseInteractor

    suspend fun signIn(login: String, password: String): PResult<Admin> {
        return withContext(Dispatchers.IO) {
            try {
                databaseInteractor.signIn(login, password)?.let {
                    it.toSuccessResult()
                } ?: Throwable(message = "Invalid pair login password").toErrorResult()
            } catch (e: Throwable) {
                e.toErrorResult()
            }
        }

    }

    suspend fun getPizzaMenu(): PResult<ResponsePizza> {
        return withContext(Dispatchers.IO) {
            try {
                ResponsePizza(
                    databaseInteractor.getPizzaMenu()
                ).toSuccessResult()
            } catch (e: Throwable) {
                e.toErrorResult()
            }
        }
    }

    suspend fun saveNewOrder(orderItem: OrderItem): PResult<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                orderItem.run {
                    return@withContext databaseInteractor.saveNewOrderMenu(
                        SaveOrderDbModel(
                            workerId = Random.nextInt(1,11),
                            orderPizzas = orderedPizzas.toStringValue().trimEnd(),
                            bill = bill,
                            date = date
                        )
                    ).toSuccessResult()
                }
            } catch (e: Throwable) {
                e.toErrorResult()
            }
        }
    }

    suspend fun getAllOrders(): PResult<ResponseOrder> {
        return withContext(Dispatchers.IO) {
            try {
                ResponseOrder(
                    databaseInteractor.getAllOrders()
                ).toSuccessResult()
            } catch (e: Throwable) {
                e.toErrorResult()
            }
        }
    }

    suspend fun cleanUpResources() {
        databaseInteractor.closeConnection()
    }

}