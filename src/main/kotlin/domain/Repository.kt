package domain

import data.DatabaseInteractor
import domain.models.Pizza
import domain.models.Worker
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

    suspend fun getPizzaMenu(): PResult<List<Pizza>> {
        return withContext(Dispatchers.IO) {
            try {
                databaseInteractor.getPizzaMenu().toSuccessResult()
            } catch (e: Throwable) {
                e.toErrorResult()
            }
        }
    }

    suspend fun cleanUpResources() {
        databaseInteractor.closeConnection()
    }

}