package dm.sample.mova.ui.screens.auth

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.EmailTextField
import dm.sample.mova.ui.components.MovaDialog
import dm.sample.mova.ui.components.MovaIcon
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.PasswordTextField
import dm.sample.mova.ui.components.SimpleHeader
import dm.sample.mova.ui.components.auth.ContinueWithSocial
import dm.sample.mova.ui.components.auth.LoginTitle
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.viewmodel.LoginViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToPinCodeCreation: () -> Unit,
    navigateToHome: () -> Unit,
    onBackClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    var isOpenRedirectDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.loginSucceed) {
        if (state.loginSucceed) {
            isOpenRedirectDialog = true
            delay(2000)
            isOpenRedirectDialog = false
            if (state.isLoginAsGuest) navigateToHome()
            else navigateToPinCodeCreation()
        }
    }

    LaunchedEffect(key1 = state.isLoginAsGuest) {
        if (!state.loginSucceed && state.isLoginAsGuest) {
            viewModel.loginAsGuest()
        }
    }

    when {
        state.isLoading -> {
            MovaLoadingScreen()
        }
        else -> {
            LoginScreenContent(
                email = state.email,
                password = state.password,
                onBackClick = { onBackClick() },
                onEmailChanged = { viewModel.onEmailChanged(it) },
                onPasswordChanged = { viewModel.onPasswordChanged(it) },
                onForgotPasswordClick = onForgotPasswordClick,
                loginUser = { viewModel.loginUser() },
                onContinueClick = { viewModel.isOpenDialog(true) },
                errorText = state.error,
                isLoginEnabled = state.isLoginEnabled,
                onSignUpClick = onSignUpClick
            )

        }
    }

    if (state.isOpenContinueWithSocialDialog) MovaDialog(
        onButtonClick = { viewModel.isOpenDialog(false) },
        onDismiss = { viewModel.isOpenDialog(false) },
        title = R.string.sorry_text,
        text = R.string.unavailable_service_text,
        icon = R.drawable.ic_dialog_ok,
        hasButton = true
    )

    if (isOpenRedirectDialog) LoginLoadingDialog()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreenContent(
    email: String,
    password: String,
    onBackClick: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    loginUser: () -> Unit,
    errorText: Int?,
    isLoginEnabled: Boolean,
    onContinueClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .semantics { testTagsAsResourceId = true }
    ) {
        SimpleHeader(onBackClick, modifier = Modifier.padding(top = 21.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MovaVerticalSpacer(height = 47.dp)
            MovaIcon(modifier = Modifier.size(80.dp))
            MovaVerticalSpacer(height = 42.dp)
            LoginTitle(titleId = R.string.login_screen_login)
            EmailTextField(
                value = email,
                onValueChange = { onEmailChanged(it) },
                labelTextId = R.string.login_screen_email,
                isError = errorText != null,
                modifier = Modifier
                    .padding(start = 22.dp, end = 22.dp, top = 32.dp)
                    .testTag("tfEmail")
            )

            PasswordTextField(
                value = password,
                onValueChange = { onPasswordChanged(it) },
                placeholderTextId = R.string.login_screen_password,
                isError = errorText != null,
                modifier = Modifier
                    .padding(start = 22.dp, end = 22.dp, top = 32.dp)
                    .testTag("tfPassword")
            )
            LoginError(errorText = errorText, onForgotPasswordClick = onForgotPasswordClick)
            MovaRedButton(
                buttonText = R.string.login_screen_sign_in,
                onClick = { loginUser() },
                isEnabled = isLoginEnabled,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .testTag("btnSingIn")
            )
            Text(
                text = stringResource(id = R.string.login_screen_forgot_password),
                style = bodyLargeSemiBold,
                color = Primary500,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .clickable(
                        onClick = { onForgotPasswordClick() },
                        indication = null,
                        interactionSource = remember { NoRippleInteractionSource() }
                    )
                    .testTag("tvForgotPassword"))

            MovaVerticalSpacer(height = 22.dp)
            MovaAuthDivider(
                textId = R.string.login_screen_continue_with,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 28.dp)
            ContinueWithSocial(onClick = onContinueClick)
            MovaVerticalSpacer(height = 30.dp)
            SignUpText(onClick = onSignUpClick, modifier = Modifier.padding(horizontal = 24.dp))
            MovaVerticalSpacer(height = 20.dp)
        }
    }
}

@Composable
private fun LoginError(
    errorText: Int?,
    onForgotPasswordClick: () -> Unit,
) {
    if (errorText != null) {
        Text(
            text = stringResource(id = errorText),
            style = bodyLargeSemiBold,
            color = Primary500,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .clickable(
                    onClick = { onForgotPasswordClick() },
                    indication = null,
                    interactionSource = remember { NoRippleInteractionSource() }
                ),
        )
    } else {
        MovaVerticalSpacer(height = 24.dp)
    }
}

@Composable
fun LoginLoadingDialog() {
    MovaDialog(
        title = R.string.congratulations_text,
        text = R.string.redirect_to_home_text,
        icon = R.drawable.ic_dialog_ok,
        hasLoader = true
    )
}

@ShowkaseComposable(
    name = "Login Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoginScreenContent(){
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        LoginScreenContent(
            email = "",
            password = "",
            onBackClick = { },
            onEmailChanged = { },
            onPasswordChanged = { },
            onForgotPasswordClick = { },
            loginUser = { },
            errorText = null,
            isLoginEnabled = true,
            onContinueClick = { },
            onSignUpClick = { },
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoginScreenError(){
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        LoginScreenContent(
            email = "",
            password = "",
            onBackClick = { },
            onEmailChanged = { },
            onPasswordChanged = { },
            onForgotPasswordClick = { },
            loginUser = { },
            errorText = R.string.login_screen_invalid_username_password,
            isLoginEnabled = true,
            onContinueClick = { },
            onSignUpClick = { },
        )
    }
}