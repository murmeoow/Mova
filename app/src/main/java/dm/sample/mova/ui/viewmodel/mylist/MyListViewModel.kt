package dm.sample.mova.ui.viewmodel.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.HttpStatusCode
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.usecase.account.IsLoginAsGuestUseCase
import dm.sample.mova.domain.usecase.auth.LogoutUserUseCase
import dm.sample.mova.domain.usecase.genres.GetGenresUseCase
import dm.sample.mova.domain.usecase.mylist.AddToMyListUseCase
import dm.sample.mova.domain.usecase.mylist.GeMyListMoviesUseCase
import dm.sample.mova.domain.usecase.mylist.RemoveFromMyListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val geMyListMoviesUseCase: GeMyListMoviesUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val removeFromMyListUseCase: RemoveFromMyListUseCase,
    private val addToMyListUseCase: AddToMyListUseCase,
    private val isLoginAsGuestUseCase: IsLoginAsGuestUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyListUiState())
    val uiState: StateFlow<MyListUiState> = _uiState.asStateFlow()

    private val _navEvent = Channel<NavEvent>()
    val navEvent = _navEvent.receiveAsFlow()

    private var allMovies: List<Movie> = emptyList()

    private val _addToFavorite = MutableSharedFlow<Long?>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        viewModelScope.launch {
            val isGuest = isLoginAsGuestUseCase()
            if (isGuest) {
                _uiState.update { it.copy(isGuestAccount = true) }
            } else {
                fetch()
            }

            _addToFavorite.collectLatest {
                it?.let { onFavouriteClick(it) }
            }
        }
    }

    private fun fetch() = viewModelScope.launch {
        var genresList = emptyList<Genre>()
        getGenresUseCase.invoke().collectLatest { resource ->
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
                    _uiState.update { it.copy(isLoading = false, isError = false) }
                    genresList = resource.data.genres
                }
            }
        }
        geMyListMoviesUseCase.invoke().collectLatest { resource ->
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
                    _uiState.update { it.copy(isLoading = false, isError = false) }
                    val moviesList = resource.data ?: emptyList()

                    if (moviesList.isEmpty()) {
                        _uiState.update { it.copy(isEmptyList = true) }
                    } else {
                        allMovies = moviesList

                        _uiState.update {
                            it.copy(
                                moviesList = moviesList,
                                filtersList = genresList,
                                moviesInFavourite = moviesList.map { it.id }
                            )
                        }
                    }
                }
            }

        }
    }

    fun onAction(event: MyListUiEvent) {
        when (event) {
            is MyListUiEvent.OnSearchClick -> {
                _uiState.update {
                    it.copy(isSearchOpen = event.isSearchOpen)
                }
                if (event.isSearchOpen.not()) {
                    _uiState.update { it.copy(searchValue = "") }
                    search()
                }
            }
            is MyListUiEvent.OnMovie -> onMovieClick(event.id)
            is MyListUiEvent.OnFavorite -> _addToFavorite.tryEmit(event.id)
            is MyListUiEvent.OnSearchValueChanged -> {
                _uiState.update { it.copy(searchValue = event.searchText) }
                search()
            }
            is MyListUiEvent.OnFilterClick -> onFilterClick(event.filter)
            MyListUiEvent.TryAgain -> fetch()
        }
    }

    private fun removeFromFavoriteUi(id: Long) {
        val newFavouritesList = _uiState.value.moviesInFavourite.toMutableList()
        newFavouritesList.remove(id)
        _uiState.update {
            it.copy(
                moviesInFavourite = newFavouritesList
            )
        }
    }

    private fun addToFavoriteUi(id: Long) {
        val newFavouritesList = _uiState.value.moviesInFavourite.toMutableList()
        newFavouritesList.add(id)
        _uiState.update {
            it.copy(
                moviesInFavourite = newFavouritesList
            )
        }
    }

    private suspend fun onFavouriteClick(id: Long) {
        val inFavourite = _uiState.value.moviesInFavourite.find { it == id } != null
        if (inFavourite) {
            removeFromFavoriteUi(id)
            removeFromMyListUseCase.invoke(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        addToFavoriteUi(id)
                        _uiState.update { it.copy(isAddInFavouriteError = true) }
                        delay(2000)
                        _uiState.update { it.copy(isAddInFavouriteError = false) }
                    }
                    is Resource.Loading, -> { /* no-op */ }
                    is Resource.Success -> { /* no-op */ }
                }
            }
        } else {
            addToFavoriteUi(id)
            addToMyListUseCase.invoke(id).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        removeFromFavoriteUi(id)
                        _uiState.update { it.copy(isAddInFavouriteError = true) }
                        delay(2000)
                        _uiState.update { it.copy(isAddInFavouriteError = false) }
                    }
                    is Resource.Loading -> { /* no-op */ }
                    is Resource.Success -> { /* no-op */ }
                }
            }
        }
    }

    private fun onFilterClick(filter: Long) {
        val newFilterList = _uiState.value.selectedFilters.toMutableList()
        val hasAllFilter = _uiState.value.selectedFilters.find { it == ALL_CATEGORY_FILTER } != null
        val isSelected = _uiState.value.selectedFilters.find { it == filter } != null
        if (isSelected) {
            newFilterList.remove(filter)
            if (newFilterList.size == 0) newFilterList.add(ALL_CATEGORY_FILTER)
        } else {
            if (hasAllFilter) newFilterList.remove(ALL_CATEGORY_FILTER)
            if (filter == ALL_CATEGORY_FILTER) {
                newFilterList.clear()
                newFilterList.add(ALL_CATEGORY_FILTER)
            } else {
                newFilterList.add(filter)
            }
        }
        _uiState.update { it.copy(selectedFilters = newFilterList.toList()) }
        search()
    }

    private fun search() {
        val searchText = _uiState.value.searchValue
        val selectedFilters = _uiState.value.selectedFilters
        val isAllCategoryFilter = selectedFilters[0] == ALL_CATEGORY_FILTER
        when {
            searchText.isNotBlank() && isAllCategoryFilter -> {
                val newList = allMovies.filter {
                    searchText.lowercase() in it.title.lowercase()
                }
                _uiState.update { it.copy(moviesList = newList) }
            }
            searchText.isNotBlank() && selectedFilters.isNotEmpty() && isAllCategoryFilter.not() -> {
                val newList = allMovies.filter { myList ->
                    searchText.lowercase() in myList.title.lowercase() && selectedFilters.any {
                        it in myList.genreIds.toSet()
                    }
                }
                _uiState.update { it.copy(moviesList = newList) }
            }
            searchText.isBlank() && isAllCategoryFilter -> {
                _uiState.update { it.copy(moviesList = allMovies) }
            }
            searchText.isBlank() && selectedFilters.isNotEmpty() && isAllCategoryFilter.not() -> {
                val newList = allMovies.filter { movie ->
                    selectedFilters.any { it in movie.genreIds.toSet() }
                }
                _uiState.update { it.copy(moviesList = newList) }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUserUseCase().collectLatest { res ->
            when(res) {
                is Resource.Error -> {
                    _uiState.update { it.copy(isError = true, isLoading = false) }
                }
                is Resource.Loading -> {
                    _uiState.update { it.copy(isError = false, isLoading = true) }
                }
                is Resource.Success -> {
                    _navEvent.send(NavEvent.StartScreen)
                }
            }
        }
    }

    private fun onMovieClick(id: Long) = viewModelScope.launch {
        _navEvent.send(NavEvent.MovieDetails(id))
    }

    sealed class NavEvent {
        data class MovieDetails(val movieId: Long) : NavEvent()
        object StartScreen : NavEvent()
    }

    companion object {
        private const val ALL_CATEGORY_FILTER = 0L
    }
}