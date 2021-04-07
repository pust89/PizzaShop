package domain

import data.DatabaseInteractor
import domain.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Репозиторий (единственно-верный источник данных в приложении)
 */
object Repository {

    private val databaseInteractor: DatabaseInteractor = DatabaseInteractor

    suspend fun signIn(login: String, password: String): PResult<Worker> {
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

    suspend fun getPizzaMenu(): PResult<List<PizzaDatabase>> {
        return withContext(Dispatchers.IO) {
            try {
                databaseInteractor.getPizzaMenu().toSuccessResult()
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
                        OrderDatabase(
                            workerId = worker.id,
                            orderPizzas = orderedPizzas.toStringValue(),
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

//    suspend fun getAllOrder(): PResult<List<OrderDatabase>> {
//        return withContext(Dispatchers.IO) {
//            try {
//                databaseInteractor.getA().toSuccessResult()
//
//            } catch (e: Throwable) {
//                e.toErrorResult()
//            }
//        }
//    }

    suspend fun cleanUpResources() {
        databaseInteractor.closeConnection()
    }

}