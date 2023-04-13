package dm.sample.mova.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark3
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.DisabledButton
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.TransparentRed
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeBold
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.theme.bodyXLargeBold
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun MovaRedButton(
    @StringRes buttonText: Int,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Primary500,
            disabledBackgroundColor = DisabledButton,
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        shape = RoundedCornerShape(100.dp),
        enabled = isEnabled,
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = modifier
            .then(
                if (isEnabled) {
                    Modifier.shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(36.dp),
                        ambientColor = Primary500,
                        spotColor = Primary500
                    )
                } else {
                    Modifier
                }
            )
            .height(54.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = buttonText),
            color = White,
            style = bodyLargeBold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun MovaTransparentRedButton(
    @StringRes buttonText: Int,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
    modifier: Modifier,
) {
    val backgroundColor = if (isDarkTheme()) Dark3 else TransparentRed
    val textColor = if (isDarkTheme()) White else Primary500
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        ),
        elevation = ButtonDefaults.elevation(0.dp),
        shape = RoundedCornerShape(100.dp),
        enabled = isEnabled,
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = modifier
            .height(54.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = buttonText),
            color = textColor,
            style = bodyLargeBold.copy(lineHeight = 22.4.sp),
        )
    }
}

@Composable
fun MovaTwoButtons(
    @StringRes firstText: Int,
    @StringRes secondText: Int,
    onFirstClick: () -> Unit,
    onSecondClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        MovaTransparentRedButton(
            buttonText = firstText,
            onClick = onFirstClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(Modifier.width(12.dp))
        MovaRedButton(
            buttonText = secondText,
            onClick = onSecondClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}


@Composable
fun MovaOutlinedButton(
    text: String,
    checkedIcon: Painter,
    nonCheckedIcon: Painter,
    isChecked: Boolean,
    onClick: () -> Unit,
) {
    val icon = if (isChecked) {
        checkedIcon
    } else {
        nonCheckedIcon
    }
    val backgroundColor = if (isChecked) Primary500 else Color.Transparent
    val borderColor = if (isChecked) Primary500 else White
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(2.dp, borderColor),
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(horizontal = 20.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = White,
            backgroundColor = backgroundColor,
        ),
        interactionSource = remember { NoRippleInteractionSource() }
    ) {
        Icon(
            painter = icon,
            contentDescription = "Button Icon",
            tint = White,
            modifier = Modifier.size(14.dp),
        )
        Spacer(Modifier.width(9.dp))
        Text(text = text, style = bodyMediumSemiBold, color = White)
    }

}


@Composable
fun MovaRoundedButton(
    text: String,
    icon: Painter? = null,
    buttonColor: Color = colors.primary,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor),
        interactionSource = remember { NoRippleInteractionSource() }
    ) {
        icon?.let {
            Image(
                painter = icon,
                contentDescription = "play icon",
                modifier = Modifier.size(14.dp),
            )
        }
        Spacer(Modifier.width(9.dp))
        Text(text = text, style = bodyMediumSemiBold)
    }
}

@Composable
fun FilterButton(
    filterName: String,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) Primary500 else colors.background
    val textColor = if (isSelected) White else Primary500
    Button(
        onClick = { onClick(isSelected.not()) },
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        interactionSource = NoRippleInteractionSource(),
        border = BorderStroke(width = 2.dp, Primary500),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
        modifier = modifier
    ) {
        Text(
            text = filterName,
            style = bodyXLargeBold.copy(
                lineHeight = 25.2.sp),
            color = textColor
        )
    }
}

@Composable
fun SelectableButton(
    buttonText: String,
    isSelected: Boolean = false,
    onClick: (Boolean) -> Unit,
) {
    val backgroundColor = if (isSelected) Primary500 else Color.Transparent
    val textColor = if (isSelected) White else Primary500
    Button(
        onClick = {
            onClick(isSelected.not())
        },
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        interactionSource = NoRippleInteractionSource(),
        border = BorderStroke(width = 2.dp, Primary500),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
        modifier = Modifier.padding(end = 6.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(
            text = buttonText,
            style = bodyLargeSemiBold.copy(
                lineHeight = 25.2.sp),
            color = textColor
        )
    }
}


@ShowkaseComposable(
    name = "Mova rounded Button",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun MovaRoundedButtonPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MovaRoundedButton(text = "Play", icon = painterResource(R.drawable.ic_circle_play)) {

        }
    }
}

@ShowkaseComposable(
    name = "Mova rounded Button",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun MovaTransparentRedButtonPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        MovaTransparentRedButton(
            buttonText = R.string.continue_text,
            onClick = {},
            isEnabled = true,
            modifier = Modifier
        )
    }
}

@ShowkaseComposable(
    name = "Mova rounded Button",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun MovaOutlinedButtonPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        Box {
            MovaOutlinedButton(
                text = "My List",
                isChecked = false,
                checkedIcon = painterResource(R.drawable.ic_outlined_heart),
                nonCheckedIcon = painterResource(R.drawable.ic_plus),
            ) {

            }
        }
    }
}
