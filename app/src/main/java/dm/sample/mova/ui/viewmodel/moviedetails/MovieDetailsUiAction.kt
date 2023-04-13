package dm.sample.mova.ui.viewmodel.moviedetails

sealed class MovieDetailsUiAction {
    object OnFavorite : MovieDetailsUiAction()
    data class OnRatingChanged(val value: Int) : MovieDetailsUiAction()
    object RateMovie : MovieDetailsUiAction()
    data class OnMovie(val movieId: Long) : MovieDetailsUiAction()
    data class OnTrailer(val key: String) : MovieDetailsUiAction()
    object FetchSimilarMovies : MovieDetailsUiAction()
    object LoadMoreSimilarMovies : MovieDetailsUiAction()
    object TryAgain : MovieDetailsUiAction()
    object DownloadMovie : MovieDetailsUiAction()
    object HideMovieDownloading : MovieDetailsUiAction()
    object CancelMovieDownloading : MovieDetailsUiAction()
    object HideDownloadingError : MovieDetailsUiAction()
    object OpenDownloadDownloadPrompt : MovieDetailsUiAction()
    object CloseDownloadDownloadPrompt : MovieDetailsUiAction()
}
