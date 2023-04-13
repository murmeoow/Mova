package dm.sample.mova.ui.viewmodel.actionmenu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.HttpStatusCode
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieCategoryType
import dm.sample.mova.domain.usecase.actionmenu.GetMovieListByCategoryUseCase
import dm.sample.mova.navigation.ARGUMENT_MOVIE_CATEGORY_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionMenuViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieListByCategoryUseCase: GetMovieListByCategoryUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(ActionMenuUiState())
    var uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<ActionMenuNavEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val lastMovies: List<Movie>
        get() = _uiState.value.movieList

    private val movieCategoryType = MovieCategoryType.fromId(
        savedStateHandle[ARGUMENT_MOVIE_CATEGORY_ID] ?: -1)

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        getMovieListByCategoryUseCase.invoke(movieCategoryType.id).collectLatest { resource ->
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
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true, isError = false) }
                }
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, isError = false) }
                    val movies = resource.data.movieListByCategory
                    _uiState.update { it.copy(
                        title = movies.name,
                        movieList = movies.movies
                    ) }
                }
            }
        }
    }

    fun onAction(event: ActionMenuUiEvent) {
        when (event) {
            is ActionMenuUiEvent.IsErrorDialogOpen -> {
                _uiState.update { it.copy(isError = event.isOpen) }
            }
            is ActionMenuUiEvent.OnMovieClick -> onMovieClick(event.movieId)
            is ActionMenuUiEvent.OnTryAgainClick -> {
                _uiState.update { it.copy(isError = false) }
                fetch()
            }
            ActionMenuUiEvent.LoadMore -> loadMore()
        }
    }

    private fun loadMore() = viewModelScope.launch {
        val page = _uiState.value.currentPage + 1
        getMovieListByCategoryUseCase.invoke(movieCategoryType.id, page).collectLatest { resource ->
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
                is Resource.Loading -> { }
                is Resource.Success -> {
                    val loadedMovies = resource.data.movieListByCategory.movies
                    val newMovieList = mutableListOf<Movie>().apply {
                        addAll(lastMovies)
                        addAll(loadedMovies)
                    }
                    _uiState.update { it.copy(
                        currentPage = resource.data.movieListByCategory.page,
                        movieList = newMovieList
                    ) }
                }
            }
        }
    }

    private fun onMovieClick(id: Long) = viewModelScope.launch {
        _uiEvent.send(ActionMenuNavEvent.MovieDetails(id))
    }

    data class ActionMenuUiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isNetworkError: Boolean = false,
        val title: String = "",
        val movieList: List<Movie> = emptyList(),
        val currentPage: Int = 1
    )
}