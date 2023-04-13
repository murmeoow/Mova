package dm.sample.mova.ui.viewmodel.mylist

import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.domain.entities.Movie

data class MyListUiState(
    val moviesList: List<Movie> = listOf(),
    val filtersList: List<Genre> = listOf(),
    val moviesInFavourite: List<Long> = listOf(),
    val selectedFilters: List<Long> = listOf(0L), // id = 0 - All category filter

    val searchValue: String = "",
    val isSearchOpen: Boolean = false,
    val isEmptyList: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isGuestAccount: Boolean = false,
    val isAddInFavouriteError: Boolean = false,
)
