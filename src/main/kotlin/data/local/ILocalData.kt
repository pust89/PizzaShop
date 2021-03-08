package data.local

import data.dto.PResult
import domain.models.Pizza

interface ILocalData {
    fun getAllPizzas():PResult<List<Pizza>>

    fun cleanUpResources()

}