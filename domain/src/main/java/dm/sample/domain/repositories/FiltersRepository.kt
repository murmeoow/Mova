package dm.sample.mova.domain.repositories

import dm.sample.mova.domain.entities.FilterCategory

interface FiltersRepository {

    suspend fun getGenresFilterCategory() : FilterCategory?

    fun getYearFilterCategory() : FilterCategory

    fun getSortFilterCategory() : FilterCategory

}