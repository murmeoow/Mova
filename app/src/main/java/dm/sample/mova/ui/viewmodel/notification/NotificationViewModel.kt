package dm.sample.mova.ui.viewmodel.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.HttpStatusCode
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.Movie
import dm.sample.mova.domain.entities.MovieChange
import dm.sample.mova.domain.usecase.account.IsLoginAsGuestUseCase
import dm.sample.mova.domain.usecase.moviedetails.GetMovieUseCase
import dm.sample.mova.domain.usecase.notification.GetMovieChangeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getMovieChangesUseCase: GetMovieChangeListUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val isLoginAsGuestUseCase: IsLoginAsGuestUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val isLoginAsGuest = isLoginAsGuestUseCase()
            if (isLoginAsGuest) {
                _uiState.update {
                    it.copy(isGuestAccount = true, isLoading = false)
                }
            } else {
                fetchChangeList()
            }
        }
    }

    fun fetchChangeList() = viewModelScope.launch {
        getMovieChangesUseCase().collectLatest { res ->
            when (res) {
                is Resource.Error -> {
                    if (res.exception.httpStatusCode == HttpStatusCode.HTTP_UNKNOWN) {
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
                    _uiState.update {
                        it.copy(isLoading = true, isError = false)
                    }
                }
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, isError = false, movieChanges = res.data)
                    }
                    fetchMovieDetails(res.data)
                }
            }
        }
    }

    private fun fetchMovieDetails(changes: List<MovieChange>) = viewModelScope.launch {
        changes.forEach { change ->
            getMovieUseCase(change.id.toInt()).collectLatest { res ->
                when(res) {
                    is Resource.Error -> {
                        _uiState.update {
                            val mutableChanges = it.movieChanges.toMutableList()
                            mutableChanges.remove(change)
                            it.copy(movieChanges = mutableChanges.toImmutableList())
                        }
                    }
                    is Resource.Loading -> { /* no-op */ }
                    is Resource.Success -> {
                        _uiState.update { uiState ->
                            val movieChange = addDetailsToChange(res.data, uiState.movieChanges)
                            val movieChanges = replaceChange(movieChange, uiState.movieChanges)
                            uiState.copy(movieChanges = movieChanges)
                        }
                    }
                }
            }
        }
    }


    private fun addDetailsToChange(details: Movie, changes: List<MovieChange>) : MovieChange {
        val change = changes.first { it.id == details.id }
        return change.copy(movie = details)
    }

    private fun replaceChange(movieChange: MovieChange, changes: List<MovieChange>) : List<MovieChange> {
        val mutable = changes.toMutableList()
        mutable.removeIf { it.id == movieChange.id }
        mutable.add(movieChange)
        return mutable.toImmutableList()
    }

    data class UiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val isNetworkError: Boolean = false,
        val movieChanges: List<MovieChange> = listOf(),
        val isGuestAccount: Boolean = false,
    )

}