package data.local

import data.dto.PResult
import domain.models.Pizza
import domain.models.User

interface ILocalData {

    suspend fun signIn(login: String, password: String): PResult<User>

    suspend fun getAllPizzas(): PResult<List<Pizza>>

    suspend fun cleanUpResources()

}