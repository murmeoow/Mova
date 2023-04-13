package dm.sample.mova.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeRegular
import dm.sample.mova.ui.utils.isDarkTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MovaDialog(
    onDismiss: () -> Unit = { },
    onButtonClick: () -> Unit = { },
    hasButton: Boolean = false,
    hasLoader: Boolean = false,
    @StringRes title: Int,
    @StringRes text: Int,
    @DrawableRes icon: Int,
    @StringRes buttonText: Int = R.string.ok_text,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isDarkTheme()) Dark2 else White
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .width(340.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(backgroundColor)
                .semantics { testTagsAsResourceId = true },
        ) {
            MovaVerticalSpacer(height = 32.dp)
            Icon(
                painter = painterResource(id = icon),
                tint = Color.Unspecified,
                contentDescription = "",
            )
            MovaVerticalSpacer(height = 16.dp)
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h4,
                color = Primary500,
                textAlign = TextAlign.Center
            )
            MovaVerticalSpacer(height = 16.dp)
            Text(
                text = stringResource(id = text),
                style = bodyLargeRegular.copy(textAlign = TextAlign.Center),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 32.dp)
            if (hasButton) MovaRedButton(
                buttonText = buttonText,
                onClick = onButtonClick,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .testTag("btnDialog")
            )
            if (hasLoader) MovaCircularProgressBar(
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 32.dp)
        }
    }
}

@Preview
@Composable
fun MovaDialogPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MovaDialog(
            onDismiss = {},
            onButtonClick = {},
            hasButton = false,
            hasLoader = true,
            title = R.string.sorry_text,
            text = R.string.unavailable_service_text,
            icon = R.drawable.ic_dialog_ok,
        )
    }
}