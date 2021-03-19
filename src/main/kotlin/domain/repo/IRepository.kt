package domain.repo

import data.dto.PResult
import domain.models.Pizza
import domain.models.User

interface IRepository {

    suspend fun signIn(login: String, password: String): PResult<User>

    suspend fun getPizzaMenu(): PResult<List<Pizza>>

    suspend fun cleanUpResources()

}