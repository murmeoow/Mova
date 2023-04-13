package dm.sample.mova.ui.viewmodel.actionmenu

sealed class ActionMenuUiEvent {
    data class OnMovieClick(val movieId: Long) : ActionMenuUiEvent()
    data class IsErrorDialogOpen(val isOpen: Boolean) : ActionMenuUiEvent()
    object OnTryAgainClick : ActionMenuUiEvent()
    object LoadMore : ActionMenuUiEvent()
}
