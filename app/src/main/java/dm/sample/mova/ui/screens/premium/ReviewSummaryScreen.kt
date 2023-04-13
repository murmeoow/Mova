package dm.sample.mova.ui.screens.premium

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaDialog
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.screens.premium.utlis.HiddenCardNumberMask
import dm.sample.mova.ui.screens.premium.utlis.isBrandMaster
import dm.sample.mova.ui.screens.premium.utlis.isBrandVisa
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeBold
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.theme.bodyMediumMedium
import dm.sample.mova.ui.utils.isDarkTheme
import dm.sample.mova.ui.viewmodel.premium.ReviewSummaryViewModel

@Composable
fun ReviewSummaryScreen(
    viewModel: ReviewSummaryViewModel = hiltViewModel(),
    cardNumber: String,
    isMonthlySubscription: Boolean,
    navigateToChangePaymentMethod: () -> Unit,
    navigateToProfile: () -> Unit,
    onBackClick: () -> Unit
) {
    var isOpenedDialog by remember{ mutableStateOf(false) }

    ReviewSummaryScreenContent(
        cardNumber = cardNumber,
        isMonthlySubscription = isMonthlySubscription,
        isOpenedDialog = isOpenedDialog,
        onChangePaymentMethod = navigateToChangePaymentMethod,
        onCongratulationClick = {
            viewModel.updateSubscriptionStatus()
            navigateToProfile()
        },
        onConfirmPaymentClick = { isOpenedDialog = true },
        onBackClick = onBackClick
    )
    BackHandler() {
        onBackClick()
    }
}

@Composable
private fun ReviewSummaryScreenContent(
    cardNumber: String,
    isMonthlySubscription: Boolean,
    isOpenedDialog: Boolean,
    onChangePaymentMethod: () -> Unit,
    onCongratulationClick: () -> Unit,
    onConfirmPaymentClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
    ) {
        HeaderWithButtonAndTitle(
            title = R.string.review_summary_title,
            onBackClick = onBackClick
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            MovaVerticalSpacer(height = 30.dp)
            SubscriptionCard(
                isMonthly = isMonthlySubscription,
                isSelected = false,
                onClick = {},
            )
            MovaVerticalSpacer(height = 32.dp)
            Card(elevation = 2.dp) {
                Column(
                    modifier = Modifier
                        .background(if (isDarkTheme()) Dark2 else White)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    MovaVerticalSpacer(height = 24.dp)
                    CheckRow(
                        category = R.string.review_summary_amount,
                        amount = if (isMonthlySubscription) {
                            R.string.subscribe_to_premium_monthly_price
                        } else {
                            R.string.subscribe_to_premium_per_year_price
                        },
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    MovaVerticalSpacer(height = 22.dp)
                    CheckRow(
                        category = R.string.review_summary_tax,
                        amount = if (isMonthlySubscription) {
                            R.string.review_summary_tax_amount_monthly
                        } else {
                            R.string.review_summary_tax_amount_yearly
                        },
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    MovaVerticalSpacer(height = 21.dp)
                    Divider(
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    MovaVerticalSpacer(height = 21.dp)
                    CheckRow(
                        category = R.string.review_summary_total,
                        amount = if (isMonthlySubscription) {
                            R.string.review_summary_total_monthly
                        } else {
                            R.string.review_summary_total_yearly
                        },
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    MovaVerticalSpacer(height = 24.dp)
                }
            }
            MovaVerticalSpacer(height = 32.dp)

            ChangeCard(
                cardNumber = cardNumber,
                onChangePaymentMethod = onChangePaymentMethod,
            )
            MovaVerticalSpacer(height = 50.dp)
            MovaRedButton(
                buttonText = R.string.review_summary_confirm,
                onClick = onConfirmPaymentClick,
            )
            MovaVerticalSpacer(height = 24.dp)
        }
        if (isOpenedDialog) MovaDialog(
            title = R.string.congratulations_text,
            text = if (isMonthlySubscription) {
                R.string.review_summary_successfully_month
            } else {
                R.string.review_summary_successfully_year
            },
            icon = R.drawable.ic_successfully_dialog,
            hasButton = true,
            onDismiss = onCongratulationClick,
            onButtonClick = onCongratulationClick,
        )
    }  
}

@Composable
private fun ChangeCard(
    cardNumber: String?,
    onChangePaymentMethod: () -> Unit,
) {
    val background = if (isDarkTheme()) Dark2 else White

    Card(
        elevation = 2.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(background, RoundedCornerShape(16.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(
                    onClick = onChangePaymentMethod,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .padding(24.dp)
        ) {
            Icon(
                painter = painterResource(id = if (isBrandMaster(cardNumber ?: "")) {
                    if (isDarkTheme()) R.drawable.ic_mastercard_dark else R.drawable.ic_mastercard
                } else if (isBrandVisa(cardNumber ?: "")) {
                    R.drawable.ic_visa
                } else { R.drawable.ic_sample_card}),
                tint = Color.Unspecified,
                contentDescription = "Paypal method",
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = cardNumber?.let { HiddenCardNumberMask().filter(AnnotatedString(it)).text.text } ?: "",
                style = MaterialTheme.typography.h6.copy(lineHeight = 21.6.sp),
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .weight(1f)

            )
            TextButton(onClick = onChangePaymentMethod) {
                Text(
                    text = stringResource(id = R.string.review_summary_change_card),
                    style = bodyLargeBold,
                    color = Primary500
                )
            }
        }
    }
}

@Composable
private fun CheckRow(
    @StringRes category: Int,
    @StringRes amount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = category),
            style = bodyMediumMedium,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(id = amount),
            style = bodyLargeSemiBold,
            color = MaterialTheme.colors.onBackground,
        )
    }
}