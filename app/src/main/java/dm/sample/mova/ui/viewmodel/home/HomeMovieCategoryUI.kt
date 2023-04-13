package dm.sample.mova.ui.viewmodel.home

import dm.sample.mova.domain.entities.Movie

data class HomeMovieCategoryUI(
    val id: Int,
    val name: String,
    val movies: List<Movie>,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)