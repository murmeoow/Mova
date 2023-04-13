package dm.sample.mova.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopStart, modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .statusBarsPadding()
            .padding(top = 10.dp)
            .semantics { testTagsAsResourceId = true }
    ) {
        BackButton(
            onClick = onBackClick,
            modifier = Modifier.padding(start = 12.dp).testTag("btnBack")
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HeaderWithButtonAndTitle(
    @StringRes title: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .statusBarsPadding()
            .padding(top = 10.dp)
            .semantics { testTagsAsResourceId = true }
    ) {
        BackButton(
            onClick = onBackClick,
            modifier = Modifier.padding(start = 12.dp).testTag("btnBack")
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun HeaderWithTitle(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 24.dp, top = 25.dp)
    ) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        interactionSource = remember { NoRippleInteractionSource() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = "",
            tint= MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
fun HeaderWithLogoTitleSearch(
    @StringRes title: Int,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 10.dp)
            .height(60.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_mova_logo),
            contentDescription = "Mova logo",
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = onSearchClick,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
