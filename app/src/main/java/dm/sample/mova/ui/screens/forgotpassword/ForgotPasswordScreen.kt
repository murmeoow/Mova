package dm.sample.mova.ui.screens.forgotpassword

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.Dark3
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.GradientRed
import dm.sample.mova.ui.theme.Grayscale200
import dm.sample.mova.ui.theme.Grayscale600
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeBold
import dm.sample.mova.ui.theme.bodyMediumMedium
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onContinueClick: (Int) -> Unit,
) {
    var isViaEmailSelected by remember { mutableStateOf(false) }
    var isViaSmsSelected by remember { mutableStateOf(true) }

    val iconId =
        if (isDarkTheme()) R.drawable.ic_forgot_password_night else R.drawable.ic_forgot_passsword

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        val (header, icon, subtitle, smsCard, emailCard, button) = createRefs()
        HeaderWithButtonAndTitle(
            title = R.string.forgot_password_screen_title,
            onBackClick = onBackClick,
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 24.dp)
                }
        )
        Icon(
            painter = painterResource(id = iconId),
            tint = Color.Unspecified,
            contentDescription = "Forgot password icon",
            modifier = Modifier
                .padding(vertical = 24.dp)
                .size(200.dp)
                .constrainAs(icon) {
                    top.linkTo(header.bottom)
                    bottom.linkTo(subtitle.top)
                    centerHorizontallyTo(parent)
                }
        )

        Text(
            text = stringResource(id = R.string.forgot_password_screen_subtitle),
            style = bodyXLargeMedium.copy(lineHeight = 25.2.sp),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(subtitle) {
                    bottom.linkTo(smsCard.top, 24.dp)
                    top.linkTo(icon.bottom)
                    centerHorizontallyTo(parent)
                }
        )

        ViaCard(
            icon = R.drawable.ic_via_sms,
            title = R.string.forgot_password_screen_via_sms,
            subtitle = R.string.forgot_password_screen_via_sms_subtitle,
            onClick = {
                isViaEmailSelected = false
                isViaSmsSelected = true
            },
            isSelected = isViaSmsSelected,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(smsCard) {
                    bottom.linkTo(emailCard.top, 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        ViaCard(
            icon = R.drawable.ic_via_email,
            title = R.string.forgot_password_screen_via_email,
            subtitle = R.string.forgot_password_screen_via_email_subtitle,
            onClick = {
                isViaEmailSelected = true
                isViaSmsSelected = false
            },
            isSelected = isViaEmailSelected,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(emailCard) {
                    bottom.linkTo(button.top, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        MovaRedButton(
            buttonText = R.string.forgot_password_screen_continue,
            onClick = {
                onContinueClick(
                    if (isViaEmailSelected) {
                        R.string.forgot_password_screen_via_email_subtitle
                    } else {
                        R.string.forgot_password_screen_via_sms_subtitle
                    }
                )
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun ViaCard(
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier,
) {
    val borderColor = if (isSelected) {
        BorderStroke(3.dp, GradientRed)
    } else {
        BorderStroke(1.dp, if (isDarkTheme()) Dark3 else Grayscale200)
    }

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = if (isDarkTheme()) Dark2 else MaterialTheme.colors.background,
        ),
        interactionSource = remember { NoRippleInteractionSource() },
        border = borderColor,
        modifier = modifier
            .height(118.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = Color.Unspecified,
            contentDescription = "",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterVertically)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(start = 20.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = stringResource(id = title),
                style = bodyMediumMedium,
                color = if (isDarkTheme()) White else Grayscale600,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(id = subtitle),
                style = bodyLargeBold,
                color = MaterialTheme.colors.onBackground,
            )
        }
    }
}

@ShowkaseComposable(
    name = "Forgot password screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Composable
fun ForgotPasswordPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ForgotPasswordScreen(
            onBackClick = { }, {},
        )
    }
}

@ShowkaseComposable(
    name = "Via Card",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun ViaCardPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ViaCard(
            icon = R.drawable.ic_via_email,
            title = R.string.forgot_password_screen_via_email,
            subtitle = R.string.forgot_password_screen_via_sms_subtitle,
            onClick = { },
            isSelected = true,
            modifier = Modifier
        )
    }
}