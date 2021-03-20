package data.repo

import data.dto.PResult
import data.local.ILocalData
import data.local.LocalData
import domain.models.Pizza
import domain.models.User
import domain.repo.IRepository
import kotlinx.coroutines.delay

object Repository : IRepository {


    private val localData: ILocalData = LocalData()

    override suspend fun signIn(login: String, password: String): PResult<User> {
        delay(1000)
        return localData.signIn(login, password)
    }

    override suspend fun getPizzaMenu(): PResult<List<Pizza>> {
        return localData.getAllPizzas()
    }

    override suspend fun cleanUpResources() {
        localData.cleanUpResources()
    }

}