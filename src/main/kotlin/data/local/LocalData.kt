package data.local

import data.dto.PResult
import domain.models.Pizza
import domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utils.toErrorResult
import utils.toSuccessResult

class LocalData(
    private val databaseConnector: DatabaseConnector = DatabaseConnector
) : ILocalData {

    override suspend fun signIn(login: String, password: String): PResult<User> {
        return withContext(Dispatchers.IO) {
            try {
                databaseConnector.signIn(login, password)?.let {
                    it.toSuccessResult()
                } ?: Throwable(message = "Invalid pair login password").toErrorResult()
            } catch (e: Throwable) {
                e.toErrorResult()
            }
        }
    }

    override suspend fun getAllPizzas(): PResult<List<Pizza>> {
        return withContext(Dispatchers.IO) {
            try {
                databaseConnector.getPizzaMenu().toSuccessResult()
            } catch (e: Throwable) {
                e.toErrorResult()
            }
        }
    }

    override suspend fun cleanUpResources() {
        databaseConnector.closeConnection()
    }

}