package dm.sample.mova.ui.screens.forgotpassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import dm.sample.mova.ui.components.PasswordTextField
import dm.sample.mova.ui.screens.auth.LoginLoadingDialog
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val PASSWORD_MIN_LENGTH = 8
private const val PASSWORD_MAX_LENGTH = 32

@Composable
fun CreateNewPasswordScreen(
    onBackClick: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val iconId = if (isDarkTheme()) R.drawable.ic_new_password_night else R.drawable.ic_new_password
    val (newPassword, setNewPassword) = remember { mutableStateOf("") }
    val (repeatPassword, setRepeatPassword) = remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(true) }
    var isLoadingDialogShown by remember { mutableStateOf(false) }
    val isContinueEnabled = newPassword.length in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH
            && repeatPassword.length in PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH

    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {

            val (header, icon, subtitle, newPasswordField,
                repeatPasswordField, rememberMe, button) = createRefs()

            HeaderWithButtonAndTitle(
                title = R.string.create_new_password_title,
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
                    .size(width = 330.dp, height = 227.dp)
                    .constrainAs(icon) {
                        top.linkTo(header.bottom)
                        bottom.linkTo(subtitle.top)
                        centerHorizontallyTo(parent)
                    }
            )

            Text(
                text = stringResource(id = R.string.create_new_password_subtitle),
                style = bodyXLargeMedium.copy(lineHeight = 25.2.sp),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .constrainAs(subtitle) {
                        bottom.linkTo(newPasswordField.top, 24.dp)
                        start.linkTo(parent.start)
                    }
            )

            PasswordTextField(
                value = newPassword,
                onValueChange = { setNewPassword(it) },
                placeholderTextId = R.string.create_account_screen_create_password,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .constrainAs(newPasswordField) {
                        bottom.linkTo(repeatPasswordField.top, 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            PasswordTextField(
                value = repeatPassword,
                onValueChange = { setRepeatPassword(it) },
                placeholderTextId = R.string.create_account_screen_repeat_password,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .constrainAs(repeatPasswordField) {
                        bottom.linkTo(rememberMe.top, 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            RememberMe(
                isChecked = isChecked,
                onCheckClick = { isChecked = !isChecked },
                modifier = Modifier
                    .constrainAs(rememberMe) {
                        bottom.linkTo(button.top, 60.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        centerHorizontallyTo(parent)
                    }
            )

            MovaRedButton(
                buttonText = R.string.forgot_password_screen_continue,
                onClick = {
                    scope.launch {
                        isLoadingDialogShown  = true
                        delay(2000)
                        isLoadingDialogShown  = true
                        navigateToLogin()
                    } },
                isEnabled = isContinueEnabled,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom, 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        if (isLoadingDialogShown) LoginLoadingDialog()
    }
}

@Composable
fun RememberMe(
    isChecked: Boolean,
    onCheckClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.wrapContentSize()
    ) {
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(3.dp, color = Primary500),
            modifier = Modifier
                .padding(end = 12.dp)
                .size(24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.background)
                .clickable(
                    onClick = { onCheckClick() },
                    indication = null,
                    interactionSource = remember { NoRippleInteractionSource() }
                )

        ) {
            if (isChecked)
                Icon(
                    painter = painterResource(id = R.drawable.ic_checkbox),
                    tint = Color.Unspecified,
                    contentDescription = "rememberMe"
                )
        }
        Text(
            text = stringResource(id = R.string.create_new_password_remember_me),
            style = bodyMediumSemiBold.copy(lineHeight = 20.sp),
            color = MaterialTheme.colors.onBackground
        )
    }
}


@ShowkaseComposable(
    name = "Create New Password Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Composable
fun CreateNewPasswordScreenPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        CreateNewPasswordScreen(onBackClick = { }, {})
    }
}

@ShowkaseComposable(
    name = "Remember me",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun RememberMePreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        RememberMe(isChecked = true, onCheckClick = {  }, modifier = Modifier)
    }
}