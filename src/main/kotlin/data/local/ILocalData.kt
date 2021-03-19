package data.local

import data.dto.PResult
import domain.models.Pizza
import domain.models.User

interface ILocalData {

    fun signIn(login: String, password: String): PResult<User>

    fun getAllPizzas(): PResult<List<Pizza>>

    fun cleanUpResources()

}