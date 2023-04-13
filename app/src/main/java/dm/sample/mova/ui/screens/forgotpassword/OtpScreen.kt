package dm.sample.mova.ui.screens.forgotpassword

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.pincode.MovaKeyBoard
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.screens.pincode.PinCodeField
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyXLargeBold
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.viewmodel.otp.OtpUiEvent
import dm.sample.mova.ui.viewmodel.otp.OtpViewModel
import dm.sample.mova.ui.viewmodel.pincode.KeyboardItem
import dm.sample.mova.ui.viewmodel.pincode.PinCodeField
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun OtpScreen(
    viewModel: OtpViewModel = hiltViewModel(),
    @StringRes stringResId: Int?,
    onBackClick: () -> Unit,
    navigateToCreateNewPassword: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.navEvent.collect { event ->
            if (event) navigateToCreateNewPassword()
        }
    }

    OtpScreenContent(
        stringResId = stringResId,
        firstValue = state.firstValue,
        secondValue = state.secondValue,
        thirdValue = state.thirdValue,
        fourthValue = state.fourthValue,
        hasError = state.isError,
        hasDelete = state.hasDelete,
        timerCount = state.timerCount,
        isResendCode = state.isResendCode,
        onKeyBoardClick = { viewModel.onEvent(OtpUiEvent.KeyBoardClick(it)) },
        otpCodeChanged = { field, value ->
            viewModel.onEvent(OtpUiEvent.ValueChanged(field, value))
        },
        onBackClick = onBackClick,
        onVerifyClick = { viewModel.onEvent(OtpUiEvent.VerifyOtp) },
        onResendCodeClick = { viewModel.onEvent(OtpUiEvent.ResendCode) },
    )
}

@Composable
private fun OtpScreenContent(
    @StringRes stringResId: Int?,
    firstValue: String,
    secondValue: String,
    thirdValue: String,
    fourthValue: String,
    hasError: Boolean,
    timerCount: Int,
    hasDelete: Boolean,
    isResendCode: Boolean,
    otpCodeChanged: (PinCodeField, String) -> Unit,
    onKeyBoardClick: (KeyboardItem) -> Unit,
    onBackClick: () -> Unit,
    onVerifyClick: () -> Unit,
    onResendCodeClick: () -> Unit
) {
    val isVerifyEnabled = fourthValue.isNotEmpty()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        val (header, subtitle, email, otpField, timer, error, keyboard, button) = createRefs()

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

        Text(
            text = stringResource(id = R.string.otp_screen_code_send),
            style = bodyXLargeMedium.copy(lineHeight = 25.2.sp),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .constrainAs(subtitle) {
                    bottom.linkTo(otpField.top)
                    top.linkTo(header.bottom)
                    centerHorizontallyTo(parent)
                }
        )
        Text(
            text = stringResId?.let { stringResource(id = it) } ?: "",
            style = bodyXLargeBold.copy(lineHeight = 25.2.sp),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .constrainAs(email) {
                    top.linkTo(subtitle.bottom)
                    centerHorizontallyTo(parent)
                }
        )

        PinCodeField(
            firstValue = firstValue,
            secondValue = secondValue,
            thirdValue = thirdValue,
            fourthValue = fourthValue,
            hasError = hasError,
            isInPinCodeMode = false,
            firstValueChanged = { otpCodeChanged(PinCodeField.FIRST, it) },
            secondValueChanged = { otpCodeChanged(PinCodeField.SECOND, it) },
            thirdValueChanged = { otpCodeChanged(PinCodeField.THIRD, it) },
            fourthValueChanged = { otpCodeChanged(PinCodeField.FOURTH, it) },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(otpField) {
                    top.linkTo(email.bottom)
                    if (hasError) {
                        bottom.linkTo(error.top)
                    } else {
                        bottom.linkTo(timer.top)
                    }
                }
        )

        if (hasError) {
            Text(
                text = stringResource(id = R.string.otp_screen_invalid_code),
                style = bodyXLargeMedium.copy(lineHeight = 25.2.sp),
                color = Primary500,
                modifier = Modifier
                    .clickable(
                        onClick = { if (isResendCode) onResendCodeClick() },
                        indication = null,
                        interactionSource = remember { NoRippleInteractionSource() }
                    )
                    .constrainAs(error) {
                        top.linkTo(otpField.bottom)
                        bottom.linkTo(button.top)
                        centerHorizontallyTo(parent)
                    }
            )
        } else {
            Text(
                text = buildAnnotatedString {
                    if (isResendCode) {
                        withStyle(style = SpanStyle(color = Primary500)) {
                            append(stringResource(id = R.string.otp_screen_resend_code))
                        }
                    } else {
                        append(stringResource(id = R.string.otp_screen_resend_code_in))
                        withStyle(style = SpanStyle(color = Primary500)) {
                            append(" $timerCount ")
                        }
                        append(stringResource(id = R.string.otp_screen_resend_code_seconds))
                    }

                },
                style = bodyXLargeMedium.copy(lineHeight = 25.2.sp),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .clickable(
                        onClick = { if (isResendCode) onResendCodeClick() },
                        indication = null,
                        interactionSource = remember { NoRippleInteractionSource() }
                    )
                    .constrainAs(timer) {
                        top.linkTo(otpField.bottom)
                        bottom.linkTo(button.top)
                        centerHorizontallyTo(parent)
                    }
            )
        }

        MovaRedButton(
            buttonText = R.string.otp_screen_verify,
            onClick = onVerifyClick,
            isEnabled = isVerifyEnabled,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(button) {
                    bottom.linkTo(keyboard.top, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        MovaKeyBoard(
            hasDelete = hasDelete,
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
            modifier = Modifier
                .constrainAs(keyboard) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@ShowkaseComposable(
    name = "Otp Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Composable
fun OtpScreenPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        OtpScreen(
            stringResId = R.string.forgot_password_screen_via_email_subtitle,
            onBackClick = { /*TODO*/ }) {
        }
    }
}