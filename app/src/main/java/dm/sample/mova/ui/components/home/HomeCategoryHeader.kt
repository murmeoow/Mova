package dm.sample.mova.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Grayscale900
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.utils.isDarkTheme

@Composable
fun HomeCategoryHeader(categoryName: String, onClickSeeAll: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 24.dp)
            .padding(top = 14.dp, bottom = 14.dp),
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.h5,
            color = if (isDarkTheme()) { White } else { Grayscale900 },
        )
        TextButton(
            onClick = onClickSeeAll,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Text(text = stringResource(R.string.see_all),
                style = bodyMediumSemiBold,
                color = MaterialTheme.colors.primary)
        }
    }

}
