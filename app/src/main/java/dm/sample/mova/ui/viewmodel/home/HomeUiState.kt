package dm.sample.mova.ui.viewmodel.home

import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieCategoryType
import dm.sample.mova.ui.viewmodel.home.HomeMovieCategoryUI
import dm.sample.mova.ui.viewmodel.home.HomeUiError

data class HomeUiState(
    val recommendedMovie: Movie? = null,
    val isRecommendedMovieInFavourite: Boolean = false,
    val isLoading: Boolean = false,
    val error: HomeUiError? = null,
    val hasNotification: Boolean = false,
    val isAddInFavouriteError: Boolean = false,
    val isGuestAccount: Boolean = false,
    val nowPlayingMovies: HomeMovieCategoryUI = HomeMovieCategoryUI(
        id = MovieCategoryType.NowPlaying.id,
        name = MovieCategoryType.NowPlaying.categoryName,
        movies = emptyList(),
    ),
    val topRatedMovies: HomeMovieCategoryUI = HomeMovieCategoryUI(
        id = MovieCategoryType.TopRated.id,
        name = MovieCategoryType.TopRated.categoryName,
        movies = emptyList(),
    ),
    val popularMovies: HomeMovieCategoryUI = HomeMovieCategoryUI(
        id = MovieCategoryType.Popular.id,
        name = MovieCategoryType.Popular.categoryName,
        movies = emptyList(),
    ),
    val upcomingMovies: HomeMovieCategoryUI = HomeMovieCategoryUI(
        id = MovieCategoryType.Upcoming.id,
        name = MovieCategoryType.Upcoming.categoryName,
        movies = emptyList(),
    )
)