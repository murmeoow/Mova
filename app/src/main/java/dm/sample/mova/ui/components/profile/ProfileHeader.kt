package dm.sample.mova.ui.components.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier, // todo edit mode
    isEditMode: Boolean,
    onBackClick: () -> Unit,
) {
    val iconRes = if (isEditMode) R.drawable.ic_arrow_left else R.drawable.ic_mova_logo
    val titleRes = if (isEditMode) R.string.profile_edit_title else R.string.profile_title
    val iconTint = if (isEditMode) MaterialTheme.colors.onBackground else Color.Unspecified
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = "Mova logo",
            tint = iconTint,
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    onClick = { if (isEditMode) onBackClick() },
                    indication = null,
                    interactionSource = remember { NoRippleInteractionSource() }
                )
        )
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(Modifier.weight(1f))
    }
}