package domain.repo

import data.dto.PResult
import domain.models.Pizza

interface IRepository {

    fun getPizzaMenu(): PResult<List<Pizza>>

    fun cleanUpResources()

}