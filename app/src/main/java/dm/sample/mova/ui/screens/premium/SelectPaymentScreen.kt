package dm.sample.mova.ui.screens.premium

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dm.sample.mova.R
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaDialog
import dm.sample.mova.ui.components.MovaRadioButton
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaTransparentRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.screens.premium.utlis.isBrandMaster
import dm.sample.mova.ui.screens.premium.utlis.isBrandVisa
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.screens.premium.utlis.HiddenCardNumberMask
import dm.sample.mova.ui.theme.Black
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.White2
import dm.sample.mova.ui.theme.bodyLargeMedium
import dm.sample.mova.ui.utils.isDarkTheme

@Composable
fun SelectPaymentScreen(
    cardNumber: String?,
    isAddedNewCard: Boolean,
    onCardCreationClick: () -> Unit,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    var isPaypalSelected by remember { mutableStateOf(false) }
    var isGooglePaySelected by remember { mutableStateOf(false) }
    var isApplePaySelected by remember { mutableStateOf(false) }
    var isNewCardSelected by remember { mutableStateOf(false) }

    var isOpenedDialog by remember { mutableStateOf(false) }
    var isAddedNewCardState by remember { mutableStateOf(isAddedNewCard) }

    val isContinueEnabled by remember {
        derivedStateOf {
            isPaypalSelected || isGooglePaySelected || isApplePaySelected || isNewCardSelected
        }
    }

    SelectPaymentScreenContent(
        cardNumber = cardNumber,
        isPaypalSelected = isPaypalSelected,
        isGooglePaySelected = isGooglePaySelected,
        isApplePaySelected = isApplePaySelected,
        isNewCardSelected = isNewCardSelected,
        isContinueEnabled = isContinueEnabled,
        isOpenedDialog = isOpenedDialog,
        onPaypalClick = {
            isPaypalSelected = true
            isGooglePaySelected = false
            isApplePaySelected = false
            isNewCardSelected = false
        },
        onGooglePayClick = {
            isPaypalSelected = false
            isGooglePaySelected = true
            isApplePaySelected = false
            isNewCardSelected = false
        },
        onApplePayClick = {
            isPaypalSelected = false
            isGooglePaySelected = false
            isApplePaySelected = true
            isNewCardSelected = false
        },
        onNewCardClick = {
            isPaypalSelected = false
            isGooglePaySelected = false
            isApplePaySelected = false
            isNewCardSelected = true
        },
        onCardCreationClick = onCardCreationClick,
        onContinueClick = {
            when {
                isPaypalSelected ||
                isGooglePaySelected ||
                isApplePaySelected -> isOpenedDialog = true
                isNewCardSelected -> onContinueClick()
            }
        },
        onCloseDialogClick = { isOpenedDialog = false },
        onBackClick = onBackClick,
    )

    if (isAddedNewCardState) MovaDialog(
        title = R.string.congratulations_text,
        text = R.string.add_new_card_congrats,
        icon = R.drawable.ic_successfully_dialog,
        hasButton = true,
        buttonText = R.string.ok_text,
        onDismiss = { isAddedNewCardState = false },
        onButtonClick = {
            isAddedNewCardState = false
            onContinueClick()
        }
    )
}

@Composable
private fun SelectPaymentScreenContent(
    cardNumber: String?,
    isPaypalSelected: Boolean,
    isGooglePaySelected: Boolean,
    isApplePaySelected: Boolean,
    isNewCardSelected: Boolean,
    isContinueEnabled: Boolean,
    isOpenedDialog: Boolean,
    onPaypalClick: () -> Unit,
    onGooglePayClick: () -> Unit,
    onApplePayClick: () -> Unit,
    onNewCardClick: () -> Unit,
    onCardCreationClick: () -> Unit,
    onCloseDialogClick: () -> Unit,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme()) MaterialTheme.colors.background else White2)
    ) {
        val (header, subtitle, paymentMethods, addBtn, continueBtn, dialog) = createRefs()

        Header(
            onBackClick = onBackClick,
            onScanClick = {},
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = stringResource(id = R.string.select_payment_subtitle),
            style = bodyLargeMedium.copy(
                lineHeight = 22.4.sp,
            ),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(subtitle) {
                    top.linkTo(header.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
                .constrainAs(paymentMethods) {
                    top.linkTo(subtitle.bottom, 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            PaymentMethod(
                iconId = R.drawable.ic_paypal,
                text = stringResource(id = R.string.select_payment_paypal),
                isSelected = isPaypalSelected,
                onClick = onPaypalClick,
            )
            MovaVerticalSpacer(height = 32.dp)
            PaymentMethod(
                iconId = R.drawable.ic_google,
                text = stringResource(id = R.string.select_payment_google_pay),
                isSelected = isGooglePaySelected,
                onClick = onGooglePayClick,
            )
            MovaVerticalSpacer(height = 32.dp)
            PaymentMethod(
                iconId = R.drawable.ic_apple,
                text = stringResource(id = R.string.select_payment_apple_pay),
                isSelected = isApplePaySelected,
                tint = if (isDarkTheme()) White else Black,
                onClick = onApplePayClick,
            )
            if (cardNumber != null) {
                MovaVerticalSpacer(height = 32.dp)
                PaymentMethod(
                    iconId = if (isBrandMaster(cardNumber)) {
                        if (isDarkTheme()) R.drawable.ic_mastercard_dark else R.drawable.ic_mastercard
                    } else if (isBrandVisa(cardNumber)) {
                        R.drawable.ic_visa
                    } else { R.drawable.ic_sample_card},
                    text = HiddenCardNumberMask().filter(AnnotatedString(cardNumber)).text.text,
                    isSelected = isNewCardSelected,
                    tint = Color.Unspecified,
                    onClick = onNewCardClick,
                )
            }
        }
        MovaTransparentRedButton(
            buttonText = R.string.select_payment_add_cart,
            onClick = onCardCreationClick,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(addBtn) {
                    top.linkTo(paymentMethods.bottom, 32.dp)
                }
        )

        MovaRedButton(
            buttonText = R.string.continue_text,
            onClick = onContinueClick,
            isEnabled = isContinueEnabled,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .constrainAs(continueBtn) {
                    bottom.linkTo(parent.bottom, 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        if (isOpenedDialog) ServiceUnavailableDialog(
            onContinueClick = onCloseDialogClick,
            onDismissClick = onCloseDialogClick,
            modifier = Modifier
                .constrainAs(dialog) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
        )
    }
}

@Composable
private fun Header(
    onBackClick: () -> Unit,
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier.padding(top = 10.dp, end = 10.dp)
    ) {
        HeaderWithButtonAndTitle(
            title = R.string.select_payment_title,
            onBackClick = onBackClick,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = onScanClick,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_scan),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Scan icon",
            )
        }
    }
}

@Composable
private fun PaymentMethod(
    @DrawableRes iconId: Int,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    tint: Color = Color.Unspecified,
) {
    val isDarkTheme = isDarkTheme()
    val background = if (isDarkTheme) Dark2 else White
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
            .padding(vertical = 29.dp, horizontal = 30.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            tint = tint,
            contentDescription = "Payment method",
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.h6.copy(lineHeight = 21.6.sp),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .weight(1f)
        )
        MovaRadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Primary500,
                unselectedColor = Primary500,
            ),
        )
    }

}

@Composable
private fun ServiceUnavailableDialog(
    onContinueClick: () -> Unit,
    onDismissClick: () -> Unit,
    modifier: Modifier
) {
    MovaDialog(
        onButtonClick = onContinueClick,
        onDismiss = onDismissClick,
        title = R.string.sorry_text,
        text = R.string.unavailable_service_text,
        icon = R.drawable.ic_dialog_ok,
        hasButton = true,
        modifier = modifier
    )
}

@Preview
@Composable
fun SelectPaymentPreview() {
    SelectPaymentScreen(null, false, {}, {}, {})
}

@Preview
@Composable
fun PaymentMethodPreview() {
    PaymentMethod(
        R.drawable.ic_paypal,
        stringResource(id = R.string.select_payment_paypal),
        true,
        {}, Color.Unspecified,
    )
}