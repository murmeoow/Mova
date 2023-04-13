package dm.sample.mova.ui.viewmodel.downloads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dm.sample.mova.domain.base.Resource
import dm.sample.mova.domain.entities.DownloadedMovie
import dm.sample.mova.domain.usecase.account.IsLoginAsGuestUseCase
import dm.sample.mova.domain.usecase.auth.LogoutUserUseCase
import dm.sample.mova.domain.usecase.downloads.DeleteDownloadedVideoUseCase
import dm.sample.mova.domain.usecase.downloads.GetAllDownloadedVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    private val isLoginAsGuestUseCase: IsLoginAsGuestUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val getAllDownloadedVideoUseCase: GetAllDownloadedVideoUseCase,
    private val deleteDownloadedVideoUseCase: DeleteDownloadedVideoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = Channel<NavEvent>()
    val navEvent = _navEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val isGuestAccount = isLoginAsGuestUseCase()
            _uiState.update {
                it.copy(
                    isGuestAccount = isGuestAccount,
                )
            }
            fetch()
        }
    }

    private fun fetch() = viewModelScope.launch {
        getAllDownloadedVideoUseCase.invoke().collectLatest { resource ->
            when (resource) {
                is Resource.Error -> _uiState.update { it.copy(isError = true, isLoading = false) }
                is Resource.Loading -> _uiState.update { it.copy(isError = false, isLoading = true) }
                is Resource.Success -> {
                    _uiState.update { it.copy(
                        videosList = resource.data,
                        isError = false,
                        isLoading = false,
                    ) }
                }
            }
        }
    }

    fun onAction(action: DownloadsUiActions) {
        when (action) {
            is DownloadsUiActions.PickVideoToDelete -> _uiState.update { it.copy(videoToDelete = action.video) }
            DownloadsUiActions.DeleteDownloadedVideo -> deleteDownloadedMovie(_uiState.value.videoToDelete)
            DownloadsUiActions.TryAgain -> fetch()
        }
    }

    private fun deleteDownloadedMovie(movie: DownloadedMovie) = viewModelScope.launch {
        deleteDownloadedVideoUseCase.invoke(movie.downloadId, movie.name)
        val newList = _uiState.value.videosList.toMutableList()
        newList.remove(movie)
        _uiState.update { it.copy(videosList = newList) }
    }

    fun logout() = viewModelScope.launch{
        logoutUserUseCase().collectLatest { res ->
            when(res) {
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(isError = true, isLoading = false)
                    }
                }
                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(isError = false, isLoading = true)
                    }
                }
                is Resource.Success -> {
                    _navEvent.send(NavEvent.StartScreen)
                }
            }
        }
    }

    data class UiState(
        val videosList: List<DownloadedMovie> = emptyList(),
        val videoToDelete: DownloadedMovie = DownloadedMovie(null, "Sample name", "", 0, 0, 0),
        val isGuestAccount : Boolean = false,
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    )

    sealed class NavEvent {
        object StartScreen: NavEvent()
    }
}