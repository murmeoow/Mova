package dm.sample.mova.ui.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.usecase.account.IsLoginAsGuestUseCase
import dm.sample.mova.domain.usecase.home.AddRemoveToMyListUseCase
import dm.sample.mova.domain.usecase.home.GetNowPlayingMoviesUseCase
import dm.sample.mova.domain.usecase.home.GetPopularMoviesUseCase
import dm.sample.mova.domain.usecase.home.GetRecommendedMovieUseCase
import dm.sample.mova.domain.usecase.home.GetTopRatedMovieUseCase
import dm.sample.mova.domain.usecase.home.GetUpcomingMoviesUseCase
import dm.sample.mova.domain.usecase.notification.IsMoviesChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addRemoveToMyListUseCase: AddRemoveToMyListUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedMovieUseCase: GetTopRatedMovieUseCase,
    private val isMoviesChangeUseCase: IsMoviesChangeUseCase,
    private val isLoginAsGuestUseCase: IsLoginAsGuestUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _addToFavorite = MutableSharedFlow<Movie?>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        fetchRecommendedMovie()
        viewModelScope.launch {
            val isLoginAsGuest = isLoginAsGuestUseCase()
            _uiState.update {
                it.copy(isGuestAccount = isLoginAsGuest)
            }
            _addToFavorite.collectLatest {
                it?.let { onAddMyList(it) }
            }
        }
    }


    fun fetchRecommendedMovie() = viewModelScope.launch {
        getRecommendedMovieUseCase().collectLatest { res ->
            when (res) {
                is Resource.Error -> {
                    val error = if (res.exception.isNetworkError) {
                        HomeUiError.NetworkError
                    } else {
                        HomeUiError.DefaultError
                    }
                    _uiState.update {
                        it.copy(isLoading = false, error = error)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true, error = null) }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            recommendedMovie = res.data.movie,
                            isRecommendedMovieInFavourite = res.data.isInFavourite,
                            isLoading = false,
                            error = null
                        )
                    }
                    fetchMovies()
                }
            }
        }
    }

    private fun fetchMovies() = viewModelScope.launch {
        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()
    }

    fun fetchNowPlayingMovies() = viewModelScope.launch {
        getNowPlayingMoviesUseCase().collectLatest { res ->
            when (res) {
                is Resource.Error -> {
                    _uiState.update {
                        val category = it.nowPlayingMovies.copy(isLoading = false, isError = true)
                        it.copy(nowPlayingMovies = category)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        val category = it.nowPlayingMovies.copy(isLoading = true)
                        it.copy(nowPlayingMovies = category)
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        val category =
                            it.nowPlayingMovies.copy(isLoading = false, movies = res.data.movies)
                        it.copy(nowPlayingMovies = category)
                    }
                }
            }
        }

        isMoviesChangeUseCase().collectLatest { res ->
            when(res) {
                is Resource.Error -> { /* no-op */}
                is Resource.Loading -> { /* no-op */}
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(hasNotification = res.data)
                    }
                }
            }
        }
    }

    fun fetchPopularMovies() = viewModelScope.launch {
        getPopularMoviesUseCase().collectLatest { res ->
            when (res) {
                is Resource.Error -> {
                    _uiState.update {
                        val category = it.popularMovies.copy(isLoading = false, isError = true)
                        it.copy(popularMovies = category)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        val category = it.popularMovies.copy(isLoading = true)
                        it.copy(popularMovies = category)
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        val category =
                            it.popularMovies.copy(isLoading = false, movies = res.data.movies)
                        it.copy(popularMovies = category)
                    }
                }
            }
        }
    }

    fun fetchTopRatedMovies() = viewModelScope.launch {
        getTopRatedMovieUseCase().collectLatest { res ->
            when (res) {
                is Resource.Error -> {
                    _uiState.update {
                        val category = it.topRatedMovies.copy(isLoading = false, isError = true)
                        it.copy(topRatedMovies = category)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        val category = it.topRatedMovies.copy(isLoading = true)
                        it.copy(topRatedMovies = category)
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        val category =
                            it.topRatedMovies.copy(isLoading = false, movies = res.data.movies)
                        it.copy(topRatedMovies = category)
                    }
                }
            }
        }
    }

    fun fetchUpcomingMovies() = viewModelScope.launch {
        getUpcomingMoviesUseCase().collectLatest { res ->
            when (res) {
                is Resource.Error -> {
                    _uiState.update {
                        val category = it.upcomingMovies.copy(isLoading = false, isError = true)
                        it.copy(upcomingMovies = category)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        val category = it.upcomingMovies.copy(isLoading = true)
                        it.copy(upcomingMovies = category)
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        val category =
                            it.upcomingMovies.copy(isLoading = false, movies = res.data.movies)
                        it.copy(upcomingMovies = category)
                    }
                }
            }
        }
    }

    fun onAddMyList(movie: Movie) = viewModelScope.launch {
        val isAddedToList = _uiState.value.isRecommendedMovieInFavourite
        _uiState.update { it.copy(
            isRecommendedMovieInFavourite = !isAddedToList
        ) }
        addRemoveToMyListUseCase.invoke(movie.id, isAddedToList).collectLatest { resource ->
            when (resource) {
                is Resource.Error ->  {
                    _uiState.update { it.copy(
                        isRecommendedMovieInFavourite = isAddedToList,
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

}