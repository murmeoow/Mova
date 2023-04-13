package dm.sample.mova.ui.viewmodel.actionmenu

sealed class ActionMenuNavEvent {
    data class MovieDetails(val movieId: Long) : ActionMenuNavEvent()
}
