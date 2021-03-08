package data.local

import data.dto.PResult
import domain.models.Pizza
import utils.toErrorResult
import utils.toSuccessResult

class LocalData(
    private val databaseConnector: DatabaseConnector = DatabaseConnector
) : ILocalData {


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