package data.local

import data.dto.PResult
import domain.models.Pizza
import domain.models.User
import utils.toErrorResult
import utils.toSuccessResult

class LocalData(
    private val databaseConnector: DatabaseConnector = DatabaseConnector
) : ILocalData {

    override fun signIn(login: String, password: String): PResult<User> {
        return try {
            databaseConnector.signIn(login, password)?.let {
                it.toSuccessResult()
            } ?: Exception(message = "Invalid pair login password").toErrorResult()
        } catch (e: Throwable) {
            e.toErrorResult()
        }
    }


    override fun getAllPizzas(): PResult<List<Pizza>> {
        return try {
            databaseConnector.getPizzaMenu().toSuccessResult()
        } catch (e: Throwable) {
            e.toErrorResult()
        }
    }

    override fun cleanUpResources() {
        databaseConnector.closeConnection()
    }

}