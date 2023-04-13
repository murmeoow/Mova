package dm.sample.mova.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.HttpStatusCode
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.FilterCategory
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.usecase.explore.GetExploreFiltersUseCase
import dm.sample.mova.domain.usecase.explore.SearchMovieUseCase
import dm.sample.mova.domain.usecase.explore.SearchMovieWithFiltersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase,
    private val searchMovieWithFiltersUseCase: SearchMovieWithFiltersUseCase,
    private val getExploreFiltersUseCase: GetExploreFiltersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = Channel<NavEvent>()
    val navEvent = _navEvent.receiveAsFlow()

    private val _filters = MutableStateFlow<List<FilterCategory>?>(null)
    val filters = _filters.asStateFlow()

    private val _selectedFilters = MutableStateFlow<List<Filter>>(listOf())
    val filter = _selectedFilters.asStateFlow()

    private val _searchText = MutableStateFlow<String?>("")
    val searchText = _searchText.asStateFlow()

    private val lastMovies: List<Movie>
        get() = _uiState.value.movies

    init {
        _searchText.debounce(1000)
            .onEach(::resetFiltersAndSearch)
            .launchIn(viewModelScope)
        fetchFilters()
    }

    fun onMovieSelect(movie: Movie) = viewModelScope.launch {
        _navEvent.send(NavEvent.MovieDetails(movie.id))
    }

    /**
     * Fetch filters for FilterDialog
     */
    private fun fetchFilters() = viewModelScope.launch {
        getExploreFiltersUseCase().collectLatest { filterCategories ->
            _filters.value = filterCategories
        }
    }

    fun getDefaultFilters(): List<Filter> {
        val defaultFilters = mutableListOf<Filter>()
        val filters = _filters.value
        filters?.forEach { filterCat ->
            filterCat.filters
                .filter { it.isDefaultSelected }
                .let { defaultFilters.addAll(it) }
        }
        return defaultFilters
    }

    fun applyFiltersAndSearch(filters: List<Filter>) {
        _uiState.update {
            it.copy(selectedFilters = filters)
        }
        _searchText.value = null
        onSearchByFilters()
    }

    fun resetFiltersAndSearch(searchText: String? = null) {
        val keyword = searchText ?: _searchText.value
        keyword?.let {
            _uiState.update {
                it.copy(selectedFilters = emptyList())
            }
            onSearchByKeyword(it)
        }
    }

    fun onRemoveFilter(filter: Filter) {
        _uiState.update { state ->
            state.copy(selectedFilters = state.selectedFilters.filter { it != filter })
        }
        onSearchByFilters()
    }

    fun onSearchTextChanged(text: String) {
        if (text.length < MAX_SEARCH_TEXT_LENGTH) {
            _searchText.value = text
        }
    }

    fun onSearchTextClear() {
        _searchText.value = ""
    }


    fun onLoadMore() {
        val page = _uiState.value.currentPage + 1
        if (_uiState.value.selectedFilters.isEmpty() && _searchText.value.isNullOrBlank().not()) {
            onLoadMoreByKeyword(page)
        } else {
            onLoadMoreByFilters(page)
        }
    }

    fun onSearch() {
        val selectedFilters = _uiState.value.selectedFilters
        val searchText = _searchText.value
        if (selectedFilters.isEmpty()) {
            searchText?.let { onSearchByKeyword(it) }
        } else {
            onSearchByFilters()
        }
    }

    private fun onSearchByKeyword(searchText: String) = viewModelScope.launch {
        searchMovieUseCase.invoke(1, searchText).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    if (resource.exception.httpStatusCode == HttpStatusCode.HTTP_UNKNOWN) {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = false, currentPage = 0, isLoading = false,)
                        }
                    } else {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = true, currentPage = 0, isLoading = false)
                        }
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true, isError = false)
                    }
                }
                is Resource.Success -> {
                    if (resource.data.results.isEmpty()) {
                        _uiState.update {
                            it.copy(isMovieNotFound = true, isLoading = false, isError = false)
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                movies = resource.data.results,
                                currentPage = resource.data.page,
                                isMovieNotFound = false,
                                isLoading = false,
                                isError = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onSearchByFilters() = viewModelScope.launch {
        val filters = _uiState.value.selectedFilters
        searchMovieWithFiltersUseCase(page = 1, filters).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    if (resource.exception.httpStatusCode == HttpStatusCode.HTTP_UNKNOWN) {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = false, currentPage = 0)
                        }
                    } else {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = true, currentPage = 0)
                        }
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isLoading = true, isError = false)
                    }
                }
                is Resource.Success -> {
                    if (resource.data.results.isEmpty()) {
                        _uiState.update {
                            it.copy(
                                isMovieNotFound = true,
                                movies = emptyList(),
                                currentPage = 0,
                                isLoading = false,
                                isError = false,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                movies = resource.data.results,
                                currentPage = resource.data.page,
                                isMovieNotFound = false,
                                isLoading = false,
                                isError = false,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onLoadMoreByKeyword(page: Int) = viewModelScope.launch {
        val searchText = _searchText.value ?: ""
        searchMovieUseCase.invoke(page, searchText).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    if (resource.exception.httpStatusCode == HttpStatusCode.HTTP_UNKNOWN) {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = false, currentPage = 0)
                        }
                    } else {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = true, currentPage = 0)
                        }
                    }
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val loadedMovies = resource.data.results
                    val movieList = mutableListOf<Movie>().apply {
                        addAll(lastMovies)
                        addAll(loadedMovies)
                    }
                    _uiState.update {
                        it.copy(
                            movies = movieList,
                            currentPage = resource.data.page,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun onLoadMoreByFilters(page: Int) = viewModelScope.launch {
        val filters = _uiState.value.selectedFilters
        searchMovieWithFiltersUseCase.invoke(page, filters).collectLatest { resource ->
            when (resource) {
                is Resource.Error -> {
                    if (resource.exception.httpStatusCode == HttpStatusCode.HTTP_UNKNOWN) {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = false, currentPage = 0)
                        }
                    } else {
                        _uiState.update {
                            it.copy(isError = true, isNetworkError = true, currentPage = 0)
                        }
                    }
                }
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val loadedMovies = resource.data.results
                    val movieList = mutableListOf<Movie>().apply {
                        addAll(lastMovies)
                        addAll(loadedMovies)
                    }
                    _uiState.update {
                        it.copy(
                            movies = movieList,
                            currentPage = resource.data.page,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    data class UiState(
        val movies: List<Movie> = emptyList(),
        val currentPage: Int = 1,
        val isError: Boolean = false,
        val isNetworkError: Boolean = false,
        val isLoading: Boolean = false,
        val isMovieNotFound: Boolean = false,
        val selectedFilters: List<Filter> = emptyList(),
    )

    sealed class NavEvent {
        data class MovieDetails(val movieId: Long) : NavEvent()
    }

    companion object {
        private const val MAX_SEARCH_TEXT_LENGTH = 64
    }
}