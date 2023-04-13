package dm.sample.mova.ui.components

import androidx.compose.runtime.Composable
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaDialog

@Composable
fun MovaErrorScreen(
    isNetworkError: Boolean,
    onDismissClick: () -> Unit,
    onTryAgainClick: () -> Unit,
) {
    val (buttonText, title, text) = if (isNetworkError) {
        arrayOf(R.string.ok_text, R.string.sorry_text, R.string.something_went_wrong_text)
    } else {
        arrayOf(R.string.ok_text, R.string.unavailable_service2_text, R.string.please_try_again_text)
    }
    MovaDialog(
        onButtonClick = onTryAgainClick,
        onDismiss = onDismissClick,
        title = title,
        text = text,
        icon = R.drawable.ic_dialog_ok,
        buttonText = buttonText,
        hasButton = true
    )
}