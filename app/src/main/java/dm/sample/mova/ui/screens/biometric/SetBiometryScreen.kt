package dm.sample.mova.ui.screens.biometric

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithTitle
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaTransparentRedButton
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.bodyXLargeRegular
import dm.sample.mova.ui.viewmodel.biometric.BiometricViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import kotlinx.coroutines.delay

@Composable
fun SetBiometryScreen(
    navigateToHome: () -> Unit,
) {
    val viewModel = hiltViewModel<BiometricViewModel>()
    val context = LocalContext.current

    var showSnackbar by remember { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = showSnackbar) {
        if (showSnackbar) {
            delay(2000)
            showSnackbar = false
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.navigateToHomeEvent.collect {
            if (it) navigateToHome()
        }
    }

    SetBiometryScreenContent(
        showSnackbar = showSnackbar,
        onSkipClick = navigateToHome,
        onContinueClick = {
            BiometryHelper.showBiometricPrompt(
                activity = context as FragmentActivity,
                callback = object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        viewModel.setBiometricStatus()
                        showLoading = true
                    }
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        showSnackbar = true
                    }
                }
            )
        }
    )

    if (showLoading) {
        MovaLoadingScreen()
    }
}

@Composable
private fun SetBiometryScreenContent(
    onSkipClick: () -> Unit,
    onContinueClick: () -> Unit,
    showSnackbar: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ConstraintLayout(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val (header, subtitle1, subtitle2, icon, buttons) = createRefs()

            HeaderWithTitle(
                title = R.string.biometry_screen_title,
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            BiometricSubtitle(
                textId = R.string.biometry_screen_subtitle,
                modifier = Modifier
                    .constrainAs(subtitle1) {
                        centerHorizontallyTo(parent)
                        top.linkTo(header.bottom)
                        bottom.linkTo(icon.top)
                    }
                    .padding(horizontal = 24.dp)
            )
            BiometricIcon(
                modifier = Modifier
                    .constrainAs(icon) {
                        centerHorizontallyTo(parent)
                        top.linkTo(subtitle1.bottom)
                        bottom.linkTo(subtitle2.top)
                    }
                    .padding(horizontal = 24.dp))

            BiometricSubtitle(
                textId = R.string.biometry_screen_subtitle2,
                modifier = Modifier
                    .constrainAs(subtitle2) {
                        centerHorizontallyTo(parent)
                        top.linkTo(icon.bottom)
                        bottom.linkTo(buttons.top)
                    }
                    .padding(horizontal = 24.dp)
            )
            BiometricButtons(
                onSkipClick = onSkipClick,
                onContinueClick = onContinueClick,
                modifier = Modifier
                    .constrainAs(buttons) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 48.dp)
            )
        }
        if (showSnackbar) {
            Snackbar(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(text = stringResource(id = R.string.biometry_screen_error))
            }
        }
    }

}

@Composable
private fun BiometricSubtitle(
    @StringRes textId: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = textId),
        style = bodyXLargeRegular.copy(
            lineHeight = 25.2.sp,
            textAlign = TextAlign.Center
        ),
        color = MaterialTheme.colors.onBackground,
        modifier = modifier.padding(horizontal = 24.dp)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BiometricButtons(
    onSkipClick: () -> Unit,
    onContinueClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .semantics { testTagsAsResourceId = true }
    ) {
        MovaTransparentRedButton(
            buttonText = R.string.skip_text,
            onClick = onSkipClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag("btnSkip")
        )
        Spacer(Modifier.width(12.dp))
        MovaRedButton(
            buttonText = R.string.continue_text,
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .testTag("btnContinue")
        )
    }
}

@Composable
private fun BiometricIcon(
    modifier: Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_bimotric),
        tint = Color.Unspecified,
        contentDescription = "Biometric image",
        modifier = modifier
    )
}

@ShowkaseComposable(
    name = "Biometry Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SetBiometryScreenContentPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        SetBiometryScreenContent({}, {}, false)
    }
}
