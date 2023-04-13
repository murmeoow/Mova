package dm.sample.mova.domain.usecase.explore

import dm.sample.mova.domain.entities.FilterCategory
import dm.sample.mova.domain.repositories.FiltersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExploreFiltersUseCase @Inject constructor(
    private val filtersRepository: FiltersRepository
) {

    suspend operator fun invoke() : Flow<List<FilterCategory>> = flow {
        val filterCategories = mutableListOf<FilterCategory>()
        filtersRepository.getGenresFilterCategory()?.let {
            filterCategories.add(it)
        }
        filterCategories.add(filtersRepository.getYearFilterCategory())
        filterCategories.add(filtersRepository.getSortFilterCategory())
        emit(filterCategories)
    }

}