package dm.sample.mova.ui.viewmodel.moviedetails

sealed class MovieDetailsNavEvent {
    data class TrailerWebView(val key: String) : MovieDetailsNavEvent()
    data class MovieDetails(val movieId: Long) : MovieDetailsNavEvent()
}