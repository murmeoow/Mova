package dm.sample.mova.ui.components.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dm.sample.mova.ui.components.MovaRadioButton
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyXLargeSemiBold

@Composable
fun LanguageItem(isSelected: Boolean, title: String, onSelect: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onSelect() },
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
            .padding(horizontal = 24.dp, vertical = 20.dp),
    ) {
        Text(
            text = title,
            style = bodyXLargeSemiBold,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onBackground,
        )
        MovaRadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = Primary500,
                unselectedColor = Primary500,
            ),
            interactionSource = remember { NoRippleInteractionSource() }
        )
    }
}
