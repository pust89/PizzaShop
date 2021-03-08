package data.repo

import data.dto.PResult
import data.local.ILocalData
import data.local.LocalData
import domain.models.Pizza
import domain.repo.IRepository

object Repository : IRepository {

    private val localData: ILocalData = LocalData()

    override fun getPizzaMenu(): PResult<List<Pizza>> {
        return localData.getAllPizzas()
    }

    override fun cleanUpResources() {
        localData.cleanUpResources()
    }

}