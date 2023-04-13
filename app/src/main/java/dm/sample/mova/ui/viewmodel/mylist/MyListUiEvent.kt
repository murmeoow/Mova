package dm.sample.mova.ui.viewmodel.mylist

sealed class MyListUiEvent {
    data class OnSearchClick(val isSearchOpen: Boolean) : MyListUiEvent()
    data class OnSearchValueChanged(val searchText: String) : MyListUiEvent()
    data class OnMovie(val id: Long) : MyListUiEvent()
    data class OnFavorite(val id: Long) : MyListUiEvent()
    data class OnFilterClick(val filter: Long) : MyListUiEvent()
    object TryAgain : MyListUiEvent()
}