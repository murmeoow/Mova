package dm.sample.mova.ui.viewmodel.home

sealed class HomeUiError {
    object NetworkError : HomeUiError()
    object DefaultError : HomeUiError()
}