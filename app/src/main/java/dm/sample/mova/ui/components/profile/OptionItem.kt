package dm.sample.mova.ui.components.profile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.MovaSwitch
import dm.sample.mova.ui.components.animation.HorizontalScrollAnimation
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale900
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyXLargeSemiBold
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable


@Composable
fun OptionItem(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    optionText: String,
    onClick: () -> Unit,
    value: String? = null,
    hasRightArrow: Boolean = true,
) {
    SampleOptionItem(
        modifier = modifier,
        icon = icon,
        optionText = optionText,
        onClick = onClick,
        hasRightArrow = hasRightArrow,
        value = value,
    )
}

@Composable
fun OptionRedItem(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    optionText: String,
    onClick: () -> Unit,
    value: String? = null,
    hasRightArrow: Boolean = true,
) {
    SampleOptionItem(
        modifier = modifier,
        icon = icon,
        optionText = optionText,
        onClick = onClick,
        hasRightArrow = hasRightArrow,
        value = value,
        tintColor = Primary500,
    )
}

@Composable
fun OptionToggleItem(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    optionText: String,
    isChecked: Boolean = false,
    onToggleCheckChanged: ((Boolean) -> Unit)? = null,
) {

    SampleOptionItem(
        modifier = modifier,
        icon = icon,
        optionText = optionText,
        isChecked = isChecked,
        isToggle = true,
        onToggleCheckChanged = {
            onToggleCheckChanged?.invoke(it)
        },
        onClick = {
            onToggleCheckChanged?.invoke(isChecked.not())
        }
    )
}


@Composable
private fun SampleOptionItem(
    modifier: Modifier = Modifier,
    icon: Painter?,
    optionText: String,
    onClick: () -> Unit,
    value: String? = null,
    isChecked: Boolean = false,
    isToggle: Boolean = false,
    hasRightArrow: Boolean = true,
    tintColor: Color? = null,
    onToggleCheckChanged: ((Boolean) -> Unit)? = null,
) {

    val tint = tintColor ?: if (isDarkTheme()) White else Grayscale900
    val textColor = tintColor ?: MaterialTheme.colors.onBackground
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
            .height(50.dp)
            .padding(horizontal = 24.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        icon?.let {
            Icon(
                painter = it,
                contentDescription = "settings option item",
                modifier = Modifier.size(28.dp),
                tint = tint
            )
        }
        Text(
            text = optionText,
            style = bodyXLargeSemiBold,
            modifier = if (icon == null) {
                Modifier
            } else {
                Modifier.padding(start = 20.dp)
            },
            color = textColor,
        )

        if (value == null) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            HorizontalScrollAnimation(
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f),
                contentAlignment = Alignment.CenterEnd,
                content = {
                    Text(
                        text = value,
                        style = bodyXLargeSemiBold,
                        overflow = TextOverflow.Ellipsis,
                        color = textColor,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }

        if (isToggle) {
            MovaSwitch(
                checked = isChecked,
                onCheckedChange = {
                    onToggleCheckChanged?.invoke(it)
                },
            )
        } else if (hasRightArrow) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "arrow right",
                tint = tintColor ?: MaterialTheme.colors.onBackground,
                modifier = Modifier.widthIn(min = 20.dp)
            )
        }
    }
}


@ShowkaseComposable(
    name = "Option Item",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewOptionItem() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
            OptionItem(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 20.dp),
                icon = painterResource(R.drawable.ic_person),
                optionText = "Edit Profile",
                value = "Russian (Russian Feder)",
                onClick = {

                }
            )
        }
    }
}