package dm.sample.mova.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyLargeRegular
import dm.sample.mova.ui.utils.isDarkTheme

@Composable
fun MovaEmptyListScreen(
    @StringRes titleId: Int,
    @StringRes textId: Int = R.string.my_list_empty_title,
    isDownloadScreen: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        HeaderWithLogoTitleSearch(
            title = titleId,
            onSearchClick = {},
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 24.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
        ) {
            Icon(
                painter = if (isDarkTheme()) {
                    painterResource(id = R.drawable.ic_lists_night)
                } else {
                    painterResource(id = R.drawable.ic_lists)
                },
                tint = Color.Unspecified,
                contentDescription = "empty list"
            )
            MovaVerticalSpacer(height = 44.dp)
            Text(
                text = stringResource(id = textId),
                style = MaterialTheme.typography.h4,
                color = Primary500,
                textAlign = TextAlign.Center
            )
            MovaVerticalSpacer(height = 16.dp)
            if (isDownloadScreen.not()) Text(
                text = stringResource(id = R.string.my_list_empty_subtitle),
                style = bodyLargeRegular.copy(textAlign = TextAlign.Center),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}