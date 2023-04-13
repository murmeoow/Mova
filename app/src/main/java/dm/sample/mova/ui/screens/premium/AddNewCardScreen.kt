package dm.sample.mova.ui.screens.premium

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.SampleTextField
import dm.sample.mova.ui.screens.forgotpassword.RememberMe
import dm.sample.mova.ui.screens.premium.utlis.CardNumberMask
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.addCardTitle
import dm.sample.mova.ui.theme.bodyLargeBold
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.theme.bodySmallSemiBold
import dm.sample.mova.ui.theme.bodyXSmallMedium
import dm.sample.mova.ui.screens.premium.utlis.isValidWithLuhnAlgorithm
import kotlinx.coroutines.delay
import java.time.LocalDate

private const val CARD_HOLDER_REGEX = "[^A-Za-z ]"
private const val CARD_NUMBER_REGEX = "[^0-9]"
private const val CARD_HOLDER_MAX_LENGTH = 32
private const val CARD_HOLDER_MIN_LENGTH = 2
private const val CARD_NUMBER_LENGTH = 16
private const val EXPIRY_DATE_LENGTH = 2
private const val CVV_LIMIT = 3

@Composable
fun AddNewCardScreen(
    onBackClick: () -> Unit,
    onAddClick: (String) -> Unit,
) {
    val cardNumberMask = remember { CardNumberMask() }

    var cardNumber by remember { mutableStateOf("") }
    val cardNumberOnCard = remember(cardNumber) {
        cardNumberMask.filter(AnnotatedString(cardNumber))
    }.text.text

    var expiryMonth by remember { mutableStateOf("") }
    var expiryYear by remember { mutableStateOf("") }
    val expiryDateOnCard by remember {
        derivedStateOf {
            if (expiryMonth.isNotEmpty() || expiryYear.isNotEmpty()) {
                "$expiryMonth/$expiryYear"
            } else ""
        }
    }

    val nowDate = LocalDate.now()
    val nowYear = nowDate.year % 100

    var holderName by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var isRememberMeChecked by remember { mutableStateOf(false) }

    val isAddEnabled by remember {
        derivedStateOf {
            expiryMonth.length == EXPIRY_DATE_LENGTH
            && expiryYear.length == EXPIRY_DATE_LENGTH
            && cardNumber.length == CARD_NUMBER_LENGTH
            && holderName.length in CARD_HOLDER_MIN_LENGTH..CARD_HOLDER_MAX_LENGTH
            && cvv.length == CVV_LIMIT
        }
    }
    var isCardError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = isCardError) {
        if (isCardError) {
            delay(3000)
            isCardError = false
        }
    }

    LaunchedEffect(key1 = expiryMonth,) {
        if (expiryMonth.length == EXPIRY_DATE_LENGTH) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        }
    }
    AddNewCardScreenContent(
        cardNumber = cardNumber,
        cardNumberOnCard = cardNumberOnCard,
        holderName = holderName,
        expiryMonth = expiryMonth,
        expiryYear = expiryYear,
        expiryDateOnCard = expiryDateOnCard,
        cvv = cvv,
        cardNumberMask = cardNumberMask,
        isCardError = isCardError,
        isRememberMeChecked = isRememberMeChecked,
        isAddEnabled = isAddEnabled,
        holderNameChanged = { holderName = it },
        cardNumberChanged = { cardNumber = it },
        expiryMonthChanged = {
            expiryMonth = if (it.length == 1 && it.toInt() in 3..9) {
                "0$it"
            } else if (it.length == 2 && (it.toInt() > 12 || it.takeLast(1).toInt() == 0)) {
                expiryMonth
            } else {
                it
            }
        },
        expiryYearChanged = {
            expiryYear = if ((it.length == 1 && it.toInt() != 2) || (it.length == 2 && it.toInt() < nowYear )) {
                expiryYear
            } else {
                it
            }
        },
        cvvChanged = { cvv = it },
        onContinueClick = {
            val expiryDate = LocalDate.of("20$expiryYear".toInt(), expiryMonth.toInt(), 1)
            if (cardNumber.isValidWithLuhnAlgorithm() &&
                expiryYear.toInt() >= nowYear &&
                expiryMonth.toInt() <= 12 &&
                expiryDate.isAfter(nowDate)
            ) {
                onAddClick(cardNumber)
            } else {
                isCardError = true
            }
        },
        onCheckClick = { isRememberMeChecked = isRememberMeChecked.not() },
        onBackClick = onBackClick
    )
}

@Composable
private fun AddNewCardScreenContent(
    cardNumber: String,
    cardNumberOnCard: String,
    holderName: String,
    expiryMonth: String,
    expiryYear: String,
    expiryDateOnCard: String,
    cvv: String,
    cardNumberMask: CardNumberMask,
    isCardError: Boolean,
    isRememberMeChecked: Boolean,
    isAddEnabled: Boolean,
    holderNameChanged: (String) -> Unit,
    cardNumberChanged: (String) -> Unit,
    expiryMonthChanged: (String) -> Unit,
    expiryYearChanged: (String) -> Unit,
    cvvChanged: (String) -> Unit,
    onContinueClick: () -> Unit,
    onCheckClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val cardHolderRegex = Regex(CARD_HOLDER_REGEX)
    val cardNumberRegex = Regex(CARD_NUMBER_REGEX)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Header(
            onBackClick = onBackClick,
            onScanClick = { }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())
        ) {
            MovaVerticalSpacer(height = 28.dp)
            CardInfo(
                cardNumber = cardNumberOnCard,
                holderName = holderName,
                expiryDate = expiryDateOnCard,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 24.dp)
            )
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp)
            )
            // Card Holder Name
            Text(
                text = stringResource(id = R.string.add_new_card_holder_name),
                style = addCardTitle,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(start = 24.dp)
            )
            MovaVerticalSpacer(height = 12.dp)
            SampleTextField(
                value = holderName,
                onValueChange = {
                    if (it.contains(cardHolderRegex).not()) holderNameChanged(it.take(
                        CARD_HOLDER_MAX_LENGTH
                    ))
                },
                placeholderId = R.string.add_new_card_holder_name,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 24.dp)
            //Card Number
            Text(
                text = stringResource(id = R.string.add_new_card_card_number),
                style = addCardTitle,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(start = 24.dp)
            )
            MovaVerticalSpacer(height = 12.dp)
            SampleTextField(
                value = cardNumber,
                onValueChange = {
                    if (it.contains(cardNumberRegex).not()) cardNumberChanged(it.take(
                        CARD_NUMBER_LENGTH
                    ))
                },
                visualTransformation = cardNumberMask,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                placeholderId = R.string.add_new_card_card_number,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 24.dp)
            Row(modifier = Modifier.padding(horizontal = 24.dp)) {
                // Expire date
                Column(Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.add_new_card_expiry_date),
                        style = addCardTitle,
                        color = MaterialTheme.colors.onBackground,
                    )
                    MovaVerticalSpacer(height = 12.dp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SampleTextField(
                            value = expiryMonth,
                            onValueChange = { expiryMonthChanged(it.take(2)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword
                            ),
                            placeholderId = R.string.add_new_month,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.add_new_card_slash),
                            style = bodyLargeSemiBold.copy(
                                lineHeight = 19.6.sp,
                                color = MaterialTheme.colors.onBackground
                            ),
                        )
                        SampleTextField(
                            value = expiryYear,
                            onValueChange = { expiryYearChanged(it.take(2)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword
                            ),
                            placeholderId = R.string.add_new_year,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(Modifier.width(20.dp))
                // CVV
                Column(Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.add_new_card_cvv),
                        style = addCardTitle,
                        color = MaterialTheme.colors.onBackground,
                    )
                    MovaVerticalSpacer(height = 12.dp)
                    SampleTextField(
                        value = cvv,
                        onValueChange = {
                            cvvChanged(it.take(CVV_LIMIT))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        placeholderId = R.string.add_new_card_cvv,
                    )
                }
            }
            if (isCardError) {
                Text(
                    text = stringResource(id = R.string.add_new_card_validation_error),
                    style = bodyLargeSemiBold,
                    color = Primary500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                MovaVerticalSpacer(height = 24.dp)
            }
            RememberMe(
                isChecked = isRememberMeChecked,
                onCheckClick = onCheckClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            MovaVerticalSpacer(height = 30.dp)
            MovaRedButton(
                buttonText = R.string.add_new_add,
                onClick = onContinueClick,
                isEnabled = isAddEnabled,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 24.dp)
        }
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
            title = R.string.select_payment_add_cart,
            onBackClick = onBackClick,
            modifier = Modifier.weight(1f),
        )
        IconButton(
            onClick = onScanClick,
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
private fun CardInfo(
    cardNumber: String,
    holderName: String,
    expiryDate: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_mocard),
            tint = Color.Unspecified,
            contentDescription = "Card image",
            modifier = Modifier.align(Alignment.Center)
        )
        if (cardNumber.isNotEmpty()) {
            Text(
                text = cardNumber,
                style = bodyLargeBold.copy(lineHeight = 19.2.sp),
                color = White,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterStart)
                    .padding(start = 30.dp)
            )
        } else {
            Text(
                text = stringResource(id = R.string.add_new_card_mask),
                style = MaterialTheme.typography.h1.copy(lineHeight = 57.6.sp),
                color = White,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterStart)
                    .padding(start = 30.dp)
            )
        }
        Column(
            modifier = Modifier
                .width(150.dp)
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, bottom = 30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_new_card_holder_name),
                style = bodyXSmallMedium.copy(lineHeight = 12.sp),
                color = White,
                modifier = Modifier.wrapContentSize()
            )
            MovaVerticalSpacer(height = 4.dp)
            Text(
                text = if (holderName.isNotEmpty()) {
                    holderName.uppercase()
                } else {
                    stringResource(id = R.string.add_new_card_holder_mask)
                },
                maxLines = 3,
                style = bodySmallSemiBold.copy(lineHeight = 16.8.sp),
                color = White,
                modifier = Modifier.wrapContentSize()
            )
        }
        Column(
            modifier = Modifier
                .width(86.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_new_card_expiry_date),
                style = bodyXSmallMedium.copy(lineHeight = 12.sp),
                color = White,
                modifier = Modifier.wrapContentSize()
            )
            MovaVerticalSpacer(height = 4.dp)
            Text(
                text = expiryDate.ifEmpty {
                    stringResource(id = R.string.add_new_card_expire_date_mask)
                },
                style = bodySmallSemiBold.copy(lineHeight = 16.8.sp),
                color = White,
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Preview
@Composable
fun AddNewCardScreenPreview() {
    AddNewCardScreen({}) {}
}

@Preview
@Composable
fun CardInfoPreview() {
    CardInfo(
        "5555 5555 5555 5555",
        "Andrey Andrey",
        "05/23",
        Modifier
    )
}

