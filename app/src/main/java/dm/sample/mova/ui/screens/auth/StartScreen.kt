package dm.sample.mova.ui.screens.auth

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.MovaDialog
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale500
import dm.sample.mova.ui.theme.Grayscale700
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.theme.bodyMediumRegular
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.theme.bodyXLargeSemiBold
import dm.sample.mova.ui.theme.enterAsGuest
import dm.sample.mova.ui.utils.isDarkTheme
import dm.sample.mova.ui.viewmodel.StartViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun StartScreen(
    viewModel: StartViewModel = hiltViewModel(),
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignAsGuestClick: () -> Unit,
    openShowkaseBrowser: () -> Unit,
) {
    val openDialog by viewModel.openDialog.collectAsState()

    StartScreenContent(
        onOpenDialogClick = { viewModel.isOpenDialog(it) },
        onSignInClick = onSignInClick,
        onSignUpClick = onSignUpClick,
        onSignAsGuestClick = onSignAsGuestClick,
        openShowkaseBrowser = openShowkaseBrowser,
    )

    if (openDialog) MovaDialog(
        onButtonClick = { viewModel.isOpenDialog(false) },
        onDismiss = { viewModel.isOpenDialog(false) },
        title = R.string.sorry_text,
        text = R.string.unavailable_service_text,
        icon = R.drawable.ic_dialog_ok,
        hasButton = true,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun StartScreenContent(
    onOpenDialogClick: (Boolean) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignAsGuestClick: () -> Unit,
    openShowkaseBrowser: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .semantics { testTagsAsResourceId = true },
    ) {
        Spacer(Modifier.height(50.dp))
        StartIcon()
        StartTitle()
        ContinueWithButton(
            textId = R.string.start_screen_continue_facebook,
            iconId = R.drawable.ic_facebook,
            onClick = { onOpenDialogClick(true) },
            modifier = Modifier
                .padding(bottom = 6.dp, start = 24.dp, end = 24.dp, top = 16.dp)
                .testTag("btnFacebook")
        )
        ContinueWithButton(
            textId = R.string.start_screen_continue_google,
            iconId = R.drawable.ic_google,
            onClick = { openShowkaseBrowser() },
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 24.dp)
                .testTag("btnGoogle")
        )
        ContinueWithButton(
            textId = R.string.start_screen_continue_apple,
            iconId = R.drawable.ic_apple,
            onClick = { onOpenDialogClick(true) },
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(top = 6.dp, start = 24.dp, end = 24.dp)
                .testTag("btnApple")
        )
        MovaAuthDivider(
            textId = R.string.start_screen_or,
            modifier = Modifier.padding(vertical = 42.dp, horizontal = 24.dp))
        MovaRedButton(
            buttonText = R.string.start_screen_sign_in,
            onClick = onSignInClick,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .testTag("btnSignIn")
        )
        SignUpText(onClick = onSignUpClick, modifier = Modifier.padding(top = 20.dp))

        MovaVerticalSpacer(height = 6.dp)
        TextButton(
            onClick = onSignAsGuestClick,
            interactionSource = remember { NoRippleInteractionSource() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        ) {
            Text(
                text = stringResource(R.string.start_screen_enter_as_guest),
                style = enterAsGuest,
                color = if (isDarkTheme()) MaterialTheme.colors.onBackground else Grayscale700,
            )
        }
        MovaVerticalSpacer(height = 24.dp)
    }
}

@Composable
fun StartIcon() {
    Icon(
        painter = if (isDarkTheme()) {
            painterResource(R.drawable.ic_mova_start_night)
        } else {
            painterResource(R.drawable.ic_mova_start)
        },
        tint = Color.Unspecified,
        contentDescription = "imgStart",
        modifier = Modifier.padding(top = 15.dp, bottom = 10.dp, start = 15.dp),
    )
}

@Composable
private fun StartTitle() {
    Text(
        text = stringResource(id = R.string.start_screen_title),
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
private fun ContinueWithButton(
    @StringRes textId: Int,
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
    tint: Color = Color.Unspecified,
    modifier: Modifier,
) {
    val backgroundColor = if (isDarkTheme()) Dark2 else White
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.secondary),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = iconId),
            tint = tint,
            contentDescription = "")
        Spacer(Modifier.width(10.dp))
        Text(
            text = stringResource(id = textId),
            style = bodyLargeSemiBold,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun MovaAuthDivider(
    @StringRes textId: Int,
    modifier: Modifier,
) {
    val textColor = if (isDarkTheme()) White else Grayscale700
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = textId),
            style = bodyXLargeSemiBold,
            color = textColor,
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpText(
    onClick: () -> Unit,
    modifier: Modifier,
) {
    val textColor = if (isDarkTheme()) White else Grayscale500
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.semantics { testTagsAsResourceId = true }
    ) {
        Text(
            text = stringResource(id = R.string.start_screen_message_text),
            style = bodyMediumRegular,
            color = textColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.start_screen_sign_up),
            style = bodyMediumSemiBold,
            color = Primary500,
            modifier = Modifier
                .clickable(
                    onClick = { onClick() },
                    indication = null,
                    interactionSource = remember { NoRippleInteractionSource() }
                )
                .testTag("btnSignUp")
        )
    }
}

@ShowkaseComposable(
    name = "Start Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewStartScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        StartScreenContent(
            onOpenDialogClick = {},
            onSignInClick = {},
            onSignUpClick = {},
            onSignAsGuestClick = {},
            openShowkaseBrowser = {}
        )
    }
}