package dm.sample.mova.ui.viewmodel.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.HttpStatusCode
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.usecase.account.IsLoginAsGuestUseCase
import dm.sample.mova.domain.usecase.downloads.CancelVideoDownloadUseCase
import dm.sample.mova.domain.usecase.downloads.DownloadVideoUseCase
import dm.sample.mova.domain.usecase.downloads.GetMovieDownloadIdUseCase
import dm.sample.mova.domain.usecase.downloads.IsMovieDownloadRunningUseCase
import dm.sample.mova.domain.usecase.home.AddRemoveToMyListUseCase
import dm.sample.mova.domain.usecase.moviedetails.GetMovieDetailsDataUseCase
import dm.sample.mova.domain.usecase.moviedetails.GetSimilarMoviesListUseCase
import dm.sample.mova.domain.usecase.moviedetails.RateMovieUseCase
import dm.sample.mova.navigation.ARGUMENT_MOVIE_DETAILS_ID
import dm.sample.mova.ui.viewmodel.moviedetails.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsDataUseCase: GetMovieDetailsDataUseCase,
    private val addRemoveToMyListUseCase: AddRemoveToMyListUseCase,
    private val getSimilarMoviesListUseCase: GetSimilarMoviesListUseCase,
    private val rateMovieUseCase: RateMovieUseCase,
    private val isLoginAsGuestUseCase: IsLoginAsGuestUseCase,
    private val downloadVideoUseCase: DownloadVideoUseCase,
    private val cancelVideoDownloadUseCase: CancelVideoDownloadUseCase,
    private val isMovieDownloadRunningUseCase: IsMovieDownloadRunningUseCase,
    private val getMovieDownloadIdUseCase: GetMovieDownloadIdUseCase
) : ViewModel() {

    private var movieId = savedStateHandle.get<Int>(ARGUMENT_MOVIE_DETAILS_ID) ?: 0

    private var _uiState = MutableStateFlow(MovieDetailsUiState())
    var uiState = _uiState.asStateFlow()

    private val _navEvent = Channel<MovieDetailsNavEvent>()
    val navEvent = _navEvent.receiveAsFlow()

    private val lastMovies: List<Movie>?
        get() = _uiState.value.similarMoviesList

    private var previousRating: Int? = null
    private var downloadId: Long? = null
    private var isDownloadDialogShown: Boolean = false

    private val _addToFavorite = MutableSharedFlow<Long?>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        fetch()
        viewModelScope.launch {
            _addToFavorite.collectLatest {
                it?.let { addRemoveFromFavourite() }
            }
        }
    }

    private fun fetch() = viewModelScope.launch {
        getMovieDetailsDataUseCase.invoke(movieId).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    if (resource.exception.httpStatusCode == HttpStatusCode.HTTP_UNKNOWN) {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = false, isLoading = false)
                        }
                    } else {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = true, isLoading = false)
                        }
                    }
                }
                is Resource.Loading -> _uiState.update { it.copy(isLoading = true, isError = false) }
                is Resource.Success -> {
                    val response = resource.data
                    previousRating = response.movieDetails.userRating
                    downloadId = getMovieDownloadIdUseCase(response.movieDetails.id)
                    _uiState.update { it.copy(
                        isLoading = false,
                        isError = false,
                        movie = response.toUi(),
                        trailersList = response.trailers.results,
                        castList = response.movieCast.toUi(),
                        imdbUrl = IMDB_URL + (response.movieDetails.imdbId ?: ""),
                        isInFavourite = response.isInFavourite ?: false,
                        userRating = response.movieDetails.userRating,
                        isMovieAlreadyDownloaded = downloadId != null,
                        isMovieDownloading = downloadId?.let { isMovieDownloadRunningUseCase(it) } ?: false
                    ) }
                }
            }
        }

        val isLoginAsGuest = isLoginAsGuestUseCase()
        _uiState.update { it.copy(isGuestAccount = isLoginAsGuest) }
    }

    fun onAction(action: MovieDetailsUiAction) {
        when (action) {
            is MovieDetailsUiAction.OnFavorite -> _addToFavorite.tryEmit(movieId.toLong())
            is MovieDetailsUiAction.OnRatingChanged -> userRatingChanged(action.value)
            is MovieDetailsUiAction.OnMovie -> openMovieDetails(action.movieId)
            is MovieDetailsUiAction.OnTrailer -> openTrailer(action.key)
            MovieDetailsUiAction.FetchSimilarMovies -> fetchSimilarMovies()
            MovieDetailsUiAction.RateMovie -> rateMovie()
            MovieDetailsUiAction.LoadMoreSimilarMovies -> loadMoreSimilarMovies()
            MovieDetailsUiAction.TryAgain -> fetch()
            is MovieDetailsUiAction.HideMovieDownloading -> hideMovieDownloading()
            MovieDetailsUiAction.DownloadMovie -> downloadMovie()
            MovieDetailsUiAction.CancelMovieDownloading -> cancelDownloading()
            MovieDetailsUiAction.HideDownloadingError -> _uiState.update { it.copy(isDownloadingError = false) }
            MovieDetailsUiAction.OpenDownloadDownloadPrompt -> {
                if (_uiState.value.isMovieDownloading || _uiState.value.isMovieAlreadyDownloaded) {
                    downloadMovie()
                } else {
                    _uiState.update { it.copy(isDownloadBottomSheetOpen = true) }
                }
            }
            MovieDetailsUiAction.CloseDownloadDownloadPrompt -> _uiState.update { it.copy(isDownloadBottomSheetOpen = false) }
        }
    }

    private fun fetchSimilarMovies() = viewModelScope.launch {
        if (_uiState.value.similarMoviesList == null) {
            getSimilarMoviesListUseCase.invoke(movieId).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> _uiState.update {
                        it.copy(
                            isTabsLoading = false,
                            isTabsError = true
                        )
                    }
                    is Resource.Loading -> _uiState.update { it.copy(isTabsLoading = true) }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isTabsLoading = false,
                                similarMoviesList = resource.data.results
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadMoreSimilarMovies() = viewModelScope.launch {
        val page = _uiState.value.similarMoviesPage + 1
        getSimilarMoviesListUseCase.invoke(movieId, page).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> _uiState.update {
                    it.copy(
                        isTabsError = true
                    )
                }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    val loadedMovies = resource.data.results
                    val newMovieList = mutableListOf<Movie>().apply {
                        lastMovies?.let { addAll(it) }
                        addAll(loadedMovies)
                    }
                    _uiState.update {
                        it.copy(
                            similarMoviesPage = resource.data.page,
                            similarMoviesList = newMovieList
                        )
                    }
                }
            }
        }
    }

    private fun openTrailer(key: String) = viewModelScope.launch {
        _navEvent.send(MovieDetailsNavEvent.TrailerWebView(key))
    }

    private fun openMovieDetails(movieId: Long) = viewModelScope.launch {
        _navEvent.send(MovieDetailsNavEvent.MovieDetails(movieId))
    }

    private fun addRemoveFromFavourite() = viewModelScope.launch {
        val isAddedToList = _uiState.value.isInFavourite
        _uiState.update { it.copy(
            isInFavourite = !isAddedToList
        ) }
        addRemoveToMyListUseCase.invoke(movieId.toLong(), isAddedToList).collectLatest { resource ->
            when (resource) {
                is Resource.Error ->  {
                    _uiState.update { it.copy(
                        isInFavourite = isAddedToList,
                        isAddInFavouriteError = true
                    ) }
                    delay(2000)
                    _uiState.update { it.copy(isAddInFavouriteError = false) }
                }
                is Resource.Loading -> { /* no-op */ }
                is Resource.Success -> { /* no-op */ }
            }
        }
    }

    private fun userRatingChanged(value: Int) {
        _uiState.update { it.copy(
            userRating = if (_uiState.value.userRating == value) null else value
        ) }
    }

    private fun rateMovie() = viewModelScope.launch {
        val userRating = _uiState.value.userRating
        if (previousRating != userRating) {
            previousRating = userRating
            rateMovieUseCase.invoke(userRating, movieId)
        }
    }

    private fun downloadMovie() = viewModelScope.launch {
        isDownloadDialogShown = true
        _uiState.update { it.copy(isDownloadingError = false, isMovieDownloading = true) }
        val movie = _uiState.value.movie
        downloadVideoUseCase.invoke(
            movieId = movie?.id ?: 0,
            movieTitle = movie?.title,
            posterPath = movie?.posterImageUrl
        )?.catch { throwable ->
            throwable.printStackTrace()
            _uiState.update {
                it.copy(
                    isDownloadDialogOpen = false,
                    isDownloadingError = true
                )
            }
        }?.collectLatest { response ->
            when {
                response.videoSizeLeft != response.videoSizeTotal -> {
                    downloadId = response.downloadId
                    _uiState.update {
                        it.copy(
                            videoProgressPercentage = response.videoProgressPercentage,
                            videoSizeLeft = response.videoSizeLeft,
                            videoSizeTotal = response.videoSizeTotal,
                            isDownloadDialogOpen = isDownloadDialogShown
                        )
                    }
                }
                response.videoSizeLeft == response.videoSizeTotal -> {
                    launch {
                        _uiState.update {
                            it.copy(
                                isDownloadDialogOpen = false,
                                isDownloadSnackbarShown = true
                            )
                        }
                        delay(2000)
                        _uiState.update { it.copy(isDownloadSnackbarShown = false) }
                    }
                }
            }

            if (response.isError) {
                _uiState.update {
                    it.copy(
                        isDownloadDialogOpen = false,
                        isDownloadingError = true,
                        isMovieDownloading = false,
                        isMovieAlreadyDownloaded = false
                    )
                }
            }
        }
    }

    private fun cancelDownloading() = viewModelScope.launch {
        downloadId?.let {
            cancelVideoDownloadUseCase.invoke(it)
            _uiState.update { it.copy(
                isDownloadDialogOpen = false,
                isMovieDownloading = false,
                isMovieAlreadyDownloaded = false
            ) }
            downloadId = null
        }
    }

    private fun hideMovieDownloading() = viewModelScope.launch {
        isDownloadDialogShown = false
        _uiState.update { it.copy(isDownloadDialogOpen = false) }
    }

    companion object {
        private const val IMDB_URL = "https://www.imdb.com/title/"
    }
}