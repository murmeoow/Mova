package dm.sample.mova.ui.viewmodel.moviedetails

import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieTrailersResult
import dm.sample.mova.ui.viewmodel.moviedetails.models.CastUiModel
import dm.sample.mova.ui.viewmodel.moviedetails.models.MovieDetailsUiModel

data class MovieDetailsUiState(
    val movie: MovieDetailsUiModel? = null,
    val castList: List<CastUiModel> = listOf(),
    val similarMoviesList: List<Movie>? = null,
    val trailersList: List<MovieTrailersResult>? = null,
    val imdbUrl: String = "",
    val userRating: Int? = null,
    val similarMoviesPage: Int = 1,
    val videoProgressPercentage: Int = 0,
    val videoSizeTotal: Float = 0f,
    val videoSizeLeft: Float = 0f,

    val isInFavourite: Boolean = false,
    val isTabsError: Boolean = false,
    val isTabsLoading: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isNetworkError: Boolean = false,
    val isGuestAccount: Boolean = false,
    val isAddInFavouriteError: Boolean = false,
    val isDownloadDialogOpen: Boolean = false,
    val isDownloadingError: Boolean = false,
    val isMovieAlreadyDownloaded: Boolean = false,
    val isMovieDownloading: Boolean = false,
    val isDownloadBottomSheetOpen: Boolean = false,
    val isDownloadSnackbarShown: Boolean = false
)