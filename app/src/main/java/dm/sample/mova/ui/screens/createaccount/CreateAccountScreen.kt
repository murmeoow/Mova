package dm.sample.mova.ui.screens.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.ui.components.EmailTextField
import dm.sample.mova.ui.components.MovaDialog
import dm.sample.mova.ui.components.MovaIcon
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.PasswordTextField
import dm.sample.mova.ui.components.SimpleHeader
import dm.sample.mova.ui.components.auth.ContinueWithSocial
import dm.sample.mova.ui.components.auth.LoginTitle
import dm.sample.mova.ui.screens.auth.MovaAuthDivider
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Grayscale500
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyMediumRegular
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.viewmodel.createaccount.CreateAccountViewModel

@Composable
fun CreateAccountScreen(
    onBackClick: () -> Unit,
    viewModel: CreateAccountViewModel = hiltViewModel(),
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    CreateAccountScreenContent(
        email = state.email,
        password = state.password,
        repeatedPassword = state.repeatedPassword,
        isSignUpEnabled = state.isSignUpEnabled,
        onEmailChanged = { viewModel.onEmailChanged(it) },
        onPasswordChanged = { viewModel.onPasswordChanged(it) },
        onRepeatedPassword = { viewModel.onRepeatedPasswordChanged(it) },
        onBackClick = onBackClick,
        onSignUpClick = onSignUpClick,
        onContinueWithSocialClick = { viewModel.isOpenDialog(true) },
        onSignInClick = onSignInClick,
    )

    if (state.openDialog) MovaDialog(
        onButtonClick = { viewModel.isOpenDialog(false) },
        onDismiss = { viewModel.isOpenDialog(false) },
        title = R.string.sorry_text,
        text = R.string.unavailable_service_text,
        icon = R.drawable.ic_dialog_ok,
        hasButton = true
    )
}

@Composable
fun CreateAccountScreenContent(
    email: String,
    password: String,
    repeatedPassword: String,
    isSignUpEnabled: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatedPassword: (String) -> Unit,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    onContinueWithSocialClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        SimpleHeader(onBackClick, modifier = Modifier.padding(top = 18.dp))
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MovaVerticalSpacer(height = 44.dp)

            MovaIcon(modifier = Modifier.size(80.dp))

            MovaVerticalSpacer(height = 35.dp)
            LoginTitle(titleId = R.string.create_account_screen_title)

            EmailTextField(
                value = email,
                onValueChange = { onEmailChanged(it) },
                labelTextId = R.string.login_screen_email,
                modifier = Modifier.padding(start = 22.dp, end = 22.dp, top = 23.dp)
            )

            PasswordTextField(
                value = password,
                onValueChange = { onPasswordChanged(it) },
                placeholderTextId = R.string.create_account_screen_create_password,
                modifier = Modifier.padding(start = 22.dp, end = 22.dp, top = 21.dp)
            )
            PasswordTextField(
                value = repeatedPassword,
                onValueChange = { onRepeatedPassword(it) },
                placeholderTextId = R.string.create_account_screen_repeat_password,
                modifier = Modifier.padding(start = 22.dp, end = 22.dp, top = 21.dp)
            )
            MovaVerticalSpacer(height = 24.dp)
            MovaRedButton(
                buttonText = R.string.start_screen_sign_up,
                onClick = onSignUpClick,
                isEnabled = isSignUpEnabled,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 20.dp)
            MovaAuthDivider(
                textId = R.string.login_screen_continue_with,
                modifier = Modifier.padding(all = 24.dp,)
            )
            MovaVerticalSpacer(height = 22.dp)
            ContinueWithSocial(onClick = onContinueWithSocialClick)
            SignInText(onClick = onSignInClick, modifier = Modifier.padding(all = 24.dp))
        }
    }
}

@Composable
fun SignInText(
    onClick: () -> Unit,
    modifier: Modifier
) {
    Row( horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.create_account_screen_already_have_account),
            style = bodyMediumRegular,
            color = Grayscale500
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.login_screen_sign_in),
            style = bodyMediumSemiBold,
            color = Primary500,
            modifier = Modifier.clickable(
                onClick = { onClick() },
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
        )
    }
}