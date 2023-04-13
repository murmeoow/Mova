package dm.sample.data.repositories

import android.content.Context
import dm.sample.mova.data.R
import dm.sample.data.remote.ServerApi
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.FilterCategory
import dm.sample.mova.domain.entities.FilterType
import dm.sample.mova.domain.repositories.FiltersRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val serverApi: ServerApi,
) : FiltersRepository {

    override suspend fun getGenresFilterCategory(): FilterCategory? {
        return try {
            val genresResponse = serverApi.getGenre()
            val genreFilters = genresResponse.genres.map {
                Filter(
                    text = it.name,
                    value = it.id.toString(),
                    isDefaultSelected = false,
                    filterType = FilterType.GENRE,
                )
            }
            FilterCategory(context.getString(R.string.genre), genreFilters)
        } catch (e: Exception) {
            null
        }
    }

    override fun getYearFilterCategory(): FilterCategory {
        val resultFilters = mutableListOf<Filter>()
        val now = Calendar.getInstance()
        val currentYear = now.get(Calendar.YEAR)
        for (item in 1960..currentYear) {
            resultFilters.add(
                Filter(
                    text = item.toString(),
                    value = item.toString(),
                    isDefaultSelected = false,
                    filterType = FilterType.TIME,
                )
            )
        }
        resultFilters.add(
            Filter(
                text = context.getString(R.string.all_periods),
                value = "all_periods",
                isDefaultSelected = true,
                filterType = FilterType.TIME,
            ),
        )
        resultFilters.reverse() // reverse: from currentYear to 1960
        return FilterCategory(context.getString(R.string.time_periods), resultFilters)
    }

    override fun getSortFilterCategory(): FilterCategory {
        val resultFilters = mutableListOf<Filter>()
        resultFilters.add(
            Filter(
                text = context.getString(R.string.popularity),
                value = "popularity.desc",
                isDefaultSelected = false,
                filterType = FilterType.SORT,
            ),
        )
        resultFilters.add(
            Filter(
                text = context.getString(R.string.latest_release),
                value = "release_date.desc",
                isDefaultSelected = false,
                filterType = FilterType.SORT,
            ),
        )
        return FilterCategory(context.getString(R.string.sort), resultFilters)
    }
}