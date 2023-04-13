package dm.sample.mova.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor() : ViewModel() {
    private var _openDialog = MutableStateFlow(false)
    val openDialog = _openDialog.asStateFlow()

    fun isOpenDialog(status: Boolean) {
        _openDialog.value = status
    }
}