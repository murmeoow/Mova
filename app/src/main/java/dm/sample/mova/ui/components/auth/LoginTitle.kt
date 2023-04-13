package dm.sample.mova.ui.components.auth

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginTitle(
    @StringRes titleId: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = titleId),
        style = MaterialTheme.typography.h3,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    )
}