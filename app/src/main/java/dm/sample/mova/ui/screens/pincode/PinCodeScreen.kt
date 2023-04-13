package dm.sample.mova.ui.screens.pincode

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
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
import dm.sample.mova.ui.components.DigitTextField
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.HeaderWithTitle
import dm.sample.mova.ui.components.MovaDialog
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.pincode.MovaKeyBoard
import dm.sample.mova.ui.screens.biometric.BiometryHelper
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyMediumSemiBold
import dm.sample.mova.ui.theme.bodyXLargeRegular
import dm.sample.mova.ui.utils.vibrate
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem
import dm.sample.mova.ui.viewmodel.pincode.PinCodeEvent
import dm.sample.mova.ui.viewmodel.pincode.PinCodeField
import dm.sample.mova.ui.viewmodel.pincode.PinCodeMode
import dm.sample.mova.ui.viewmodel.pincode.PinCodeNavEvent
import dm.sample.mova.ui.viewmodel.pincode.PinCodeViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun PinCodeScreen(
    viewModel: PinCodeViewModel = hiltViewModel(),
    navigateToBiometric: () -> Unit,
    navigateToStart: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var showBiometricPrompt by remember { mutableStateOf(state.isBiometricShown) }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                PinCodeNavEvent.Biometric -> navigateToBiometric()
                PinCodeNavEvent.Start -> navigateToStart()
                PinCodeNavEvent.Home -> navigateToHome()
            }
        }
    }

    PinCodeScreenContent(
        pinCodeMode = state.pinCodeMode,
        firstValue = state.firstValue,
        secondValue = state.secondValue,
        thirdValue = state.thirdValue,
        fourthValue = state.fourthValue,
        hasLogout = state.hasLogout,
        hasDelete = state.hasDelete,
        hasError = state.hasError,
        attempts = state.attempts,
        pinCodeChanged = { field, value ->
            viewModel.onEvent(PinCodeEvent.ValueChanged(field, value))
        },
        onBackClick = { viewModel.onEvent(PinCodeEvent.OnBackClick) },
        onSkipClick = { viewModel.onEvent(PinCodeEvent.OnSkipClick) },
        onContinueClick = viewModel::validatePinCode,
        onKeyBoardClick = { viewModel.onEvent(PinCodeEvent.KeyBoardClick(it)) },
    )

    when {
        state.isLoading -> MovaLoadingScreen()
        state.isAccessDenied -> AccessDeniedDialog()
        state.isNetworkError -> {
            PinCodeErrorDialog(onDismiss = {
                viewModel.onEvent(PinCodeEvent.DismissNetworkDialog)
            })
        }
    }
    if (showBiometricPrompt) {
        BiometryHelper.showBiometricPrompt(
            activity = context as FragmentActivity,
            callback = object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    showBiometricPrompt = false
                    navigateToHome()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showBiometricPrompt = false
                }
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PinCodeScreenContent(
    pinCodeMode: PinCodeMode,
    firstValue: String,
    secondValue: String,
    thirdValue: String,
    fourthValue: String,
    hasLogout: Boolean,
    hasDelete: Boolean,
    hasError: Boolean,
    attempts: Int,
    onKeyBoardClick: (KeyboardItem) -> Unit,
    pinCodeChanged: (PinCodeField, String) -> Unit,
    onBackClick: () -> Unit,
    onSkipClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    val titleTextId: Int
    val subtitleTextId: Int?
    when (pinCodeMode) {
        PinCodeMode.Apply -> {
            titleTextId = R.string.pincode_screen_apply_title
            subtitleTextId = null
        }
        PinCodeMode.Create -> {
            titleTextId = R.string.pincode_screen_create_title
            subtitleTextId = R.string.pincode_screen_create_subtitle
        }
        PinCodeMode.Confirm -> {
            titleTextId = R.string.pincode_screen_confirm_title
            subtitleTextId = R.string.pincode_screen_confirm_subtitle
        }
    }
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    if (hasError && isPreview.not()) {
        context.vibrate()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .semantics { testTagsAsResourceId = true }
    ) {
        val (header, subtitle, pincode, button, keyboard, error) = createRefs()

        PinCodeHeader(
            pinCodeMode = pinCodeMode,
            textId = titleTextId,
            onBackClick = onBackClick,
            modifier = Modifier.constrainAs(header) {
                top.linkTo(parent.top, margin = 25.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = subtitleTextId?.let { stringResource(id = it) } ?: "",
            style = bodyXLargeRegular.copy(
                lineHeight = 25.2.sp,
                textAlign = TextAlign.Center
            ),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .constrainAs(subtitle) {
                    centerHorizontallyTo(parent)
                    top.linkTo(header.bottom)
                    bottom.linkTo(pincode.top)
                }
                .padding(horizontal = 24.dp)
        )
        PinCodeField(
            firstValue = firstValue,
            secondValue = secondValue,
            thirdValue = thirdValue,
            fourthValue = fourthValue,
            hasError = hasError,
            isInPinCodeMode = true,
            firstValueChanged = { pinCodeChanged(PinCodeField.FIRST, it) },
            secondValueChanged = { pinCodeChanged(PinCodeField.SECOND, it) },
            thirdValueChanged = { pinCodeChanged(PinCodeField.THIRD, it) },
            fourthValueChanged = { pinCodeChanged(PinCodeField.FOURTH, it) },
            modifier = Modifier
                .constrainAs(pincode) {
                    centerHorizontallyTo(parent)
                    top.linkTo(subtitle.bottom)
                    bottom.linkTo(error.top)
                }
                .padding(horizontal = 24.dp)
        )
        ErrorText(
            attempts = attempts,
            hasError = hasError,
            mode = pinCodeMode,
            modifier = Modifier.constrainAs(error) {
                centerHorizontallyTo(parent)
                top.linkTo(pincode.bottom)
                bottom.linkTo(button.top)
            }
        )

        MovaRedButton(
            buttonText = R.string.continue_text,
            onClick = onContinueClick,
            isEnabled = fourthValue.isNotEmpty(),
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(keyboard.top, margin = 24.dp)
                }
                .padding(horizontal = 24.dp)
                .testTag("btnContinue")
        )
        MovaKeyBoard(
            hasLogout = hasLogout,
            hasDelete = hasDelete,
            modifier = Modifier.constrainAs(keyboard) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onOneClick = { onKeyBoardClick(KeyboardItem.ONE) },
            onTwoClick = { onKeyBoardClick(KeyboardItem.TWO) },
            onThreeClick = { onKeyBoardClick(KeyboardItem.THREE) },
            onFourClick = { onKeyBoardClick(KeyboardItem.FOUR) },
            onFiveClick = { onKeyBoardClick(KeyboardItem.FIVE) },
            onSixClick = { onKeyBoardClick(KeyboardItem.SIX) },
            onSevenClick = { onKeyBoardClick(KeyboardItem.SEVEN) },
            onEightClick = { onKeyBoardClick(KeyboardItem.EIGHT) },
            onNineClick = { onKeyBoardClick(KeyboardItem.NINE) },
            onLogoutClick = { onKeyBoardClick(KeyboardItem.LOG_OUT) },
            onZeroClick = { onKeyBoardClick(KeyboardItem.ZERO) },
            onBackSpaceClick = { onKeyBoardClick(KeyboardItem.BACKSPACE) },
            onSkipClick = onSkipClick,
        )
    }
}

@Composable
private fun PinCodeHeader(
    pinCodeMode: PinCodeMode,
    @StringRes textId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier,
) {
    when (pinCodeMode) {
        PinCodeMode.Confirm -> {
            HeaderWithButtonAndTitle(
                title = textId,
                onBackClick = onBackClick,
                modifier = modifier
            )
        }
        PinCodeMode.Create, PinCodeMode.Apply -> {
            HeaderWithTitle(title = textId, modifier = modifier)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PinCodeField(
    firstValue: String,
    secondValue: String,
    thirdValue: String,
    fourthValue: String,
    hasError: Boolean,
    isInPinCodeMode: Boolean,
    firstValueChanged: (String) -> Unit,
    secondValueChanged: (String) -> Unit,
    thirdValueChanged: (String) -> Unit,
    fourthValueChanged: (String) -> Unit,
    modifier: Modifier,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    LaunchedEffect(
        key1 = firstValue,
    ) {
        if (firstValue.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        }
    }
    LaunchedEffect(
        key1 = secondValue,
    ) {
        if (secondValue.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        } else {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Previous,
            )
        }
    }
    LaunchedEffect(
        key1 = thirdValue,
    ) {
        if (thirdValue.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        } else {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Previous,
            )
        }
    }

    LaunchedEffect(
        key1 = fourthValue,
    ) {
        if (fourthValue.isNotEmpty()) {
            focusManager.clearFocus()
        } else {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Previous,
            )
        }
    }

    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .semantics { testTagsAsResourceId = true },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DigitTextField(
            value = firstValue,
            onValueChange = firstValueChanged,
            readOnly = true,
            hasError = hasError,
            isInPinCodeMode = isInPinCodeMode,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .testTag("tfFirst")
        )
        DigitTextField(
            value = secondValue,
            readOnly = true,
            hasError = hasError,
            onValueChange = secondValueChanged,
            isInPinCodeMode = isInPinCodeMode,
            modifier = Modifier
                .weight(1f)
                .testTag("tfSecond")
        )
        DigitTextField(
            value = thirdValue,
            readOnly = true,
            hasError = hasError,
            onValueChange = thirdValueChanged,
            isInPinCodeMode = isInPinCodeMode,
            modifier = Modifier
                .weight(1f)
                .testTag("tfThird")
        )
        DigitTextField(
            value = fourthValue,
            readOnly = true,
            hasError = hasError,
            onValueChange = fourthValueChanged,
            isInPinCodeMode = isInPinCodeMode,
            modifier = Modifier
                .weight(1f)
                .testTag("tfFourth")
        )
    }
}

@Composable
private fun PinCodeErrorDialog(
    onDismiss: () -> Unit,
) {
    MovaDialog(
        onButtonClick = onDismiss,
        onDismiss = onDismiss,
        title = R.string.sorry_text,
        text = R.string.check_internet_text,
        icon = R.drawable.ic_dialog_ok,
        buttonText = R.string.try_again_text,
    )
}

@Composable
private fun AccessDeniedDialog() {
    MovaDialog(
        title = R.string.access_denied_text,
        text = R.string.redirect_to_start_text,
        icon = R.drawable.ic_dialog_ok,
        buttonText = R.string.try_again_text,
        hasLoader = true
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ErrorText(
    attempts: Int,
    mode: PinCodeMode,
    hasError: Boolean,
    modifier: Modifier,
) {
    val errorText = if (mode == PinCodeMode.Apply) {
        pluralStringResource(R.plurals.pincode_screen_apply_error, attempts, attempts)
    } else {
        stringResource(id = R.string.pincode_screen_do_not_match)
    }
    Text(
        text = if (hasError) errorText else "",
        style = bodyMediumSemiBold,
        color = Primary500,
        modifier = modifier
    )
}


@ShowkaseComposable(
    name = "Pin Code Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PinCodeScreenPreview() {
    MovaTheme {
        PinCodeScreenContent(
            pinCodeMode = PinCodeMode.Create,
            firstValue = "",
            secondValue = "",
            thirdValue = "",
            fourthValue = "",
            hasLogout = false,
            hasDelete = true,
            hasError = false,
            attempts = 1,
            onKeyBoardClick = { },
            pinCodeChanged = { _, _ -> },
            onBackClick = { },
            onSkipClick = { }) {

        }
    }
}


@ShowkaseComposable(
    name = "Pin Code Screen Error",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PinCodeScreenErrorPreview() {
    MovaTheme {
        PinCodeScreenContent(
            pinCodeMode = PinCodeMode.Create,
            firstValue = "1",
            secondValue = "2",
            thirdValue = "3",
            fourthValue = "4",
            hasLogout = false,
            hasDelete = true,
            hasError = true,
            attempts = 4,
            onKeyBoardClick = { },
            pinCodeChanged = { _, _ -> },
            onBackClick = { },
            onSkipClick = { }) {

        }
    }
}