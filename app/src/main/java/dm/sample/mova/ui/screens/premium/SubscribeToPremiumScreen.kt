package dm.sample.mova.ui.screens.premium

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.SimpleHeader
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.GradientRed
import dm.sample.mova.ui.theme.Grayscale700
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.TransparentRed
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeMedium
import dm.sample.mova.ui.theme.bodyXLargeMedium
import dm.sample.mova.ui.utils.isDarkTheme

@Composable
fun SubscribeToPremiumScreen(
    onBackClick: () -> Unit,
    onSelectPaymentMethodClick: (isMonthly: Boolean) -> Unit
) {
    var perMonthSelected by remember { mutableStateOf(false) }
    var perYearSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        SimpleHeader(onBackClick = onBackClick)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            MovaVerticalSpacer(height = 24.dp)
            Text(
                text = stringResource(id = R.string.subscribe_to_premium_title),
                style = MaterialTheme.typography.h3.copy(
                    lineHeight = 38.4.sp,
                ),
                color = Primary500,
            )
            MovaVerticalSpacer(height = 12.dp)
            Text(
                text = stringResource(id = R.string.subscribe_to_premium_subtitle),
                style = bodyLargeMedium.copy(
                    lineHeight = 22.4.sp,
                    textAlign = TextAlign.Center
                ),
                color = MaterialTheme.colors.onBackground,
            )
            MovaVerticalSpacer(height = 24.dp)
            SubscriptionCard(
                isSelected = perMonthSelected,
                isMonthly = true,
                onClick = {
                    perMonthSelected = true
                    perYearSelected = false
                    onSelectPaymentMethodClick(true)
                }
            )
            MovaVerticalSpacer(height = 24.dp)
            SubscriptionCard(
                isSelected = perYearSelected,
                isMonthly = false,
                onClick = {
                    perMonthSelected = false
                    perYearSelected = true
                    onSelectPaymentMethodClick(false)
                }
            )
            MovaVerticalSpacer(height = 48.dp)
        }
    }
}

@Composable
fun SubscriptionCard(
    isSelected: Boolean,
    isMonthly: Boolean,
    onClick: () -> Unit,
) {
    val price = if (isMonthly) {
        R.string.subscribe_to_premium_monthly_price
    } else {
        R.string.subscribe_to_premium_per_year_price
    }
    val backgroundColor = if (isSelected) TransparentRed else MaterialTheme.colors.surface
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember{ NoRippleInteractionSource() }
            )
            .background(backgroundColor, RoundedCornerShape(32.dp))
            .border(
                border = BorderStroke(2.dp, GradientRed),
                shape = RoundedCornerShape(32.dp),
            ),
    ) {
        MovaVerticalSpacer(height = 31.dp)
        Icon(
            painter = painterResource(id = R.drawable.ic_crown),
            contentDescription = "premium crown",
            tint = Color.Unspecified
        )
        MovaVerticalSpacer(height = 24.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentSize()
        ) {
            Text(
                text = stringResource(id = price),
                style = MaterialTheme.typography.h3.copy(lineHeight = 38.4.sp),
                color = MaterialTheme.colors.onBackground
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(id = if (isMonthly) {
                    R.string.subscribe_to_premium_month
                } else {
                    R.string.subscribe_to_premium_year
                }
                ),
                style = bodyXLargeMedium.copy(lineHeight = 25.2.sp),
                color = if (isDarkTheme()) White else Grayscale700
            )
        }
        MovaVerticalSpacer(height = 16.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        MovaVerticalSpacer(height = 21.dp)
        SubscriptionTerms(stringId = R.string.subscribe_to_premium_subscription_terms_1)
        MovaVerticalSpacer(height = 14.dp)
        SubscriptionTerms(stringId = R.string.subscribe_to_premium_subscription_terms_2)
        MovaVerticalSpacer(height = 14.dp)
        SubscriptionTerms(stringId = R.string.subscribe_to_premium_subscription_terms_3)
        MovaVerticalSpacer(height = 30.dp)
    }
}

@Composable
private fun SubscriptionTerms(
    @StringRes stringId: Int,
) {
    Row(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            tint = Color.Unspecified,
            contentDescription = "check icon"
        )
        Spacer(Modifier.width(24.dp))
        Text(
            text = stringResource(id = stringId),
            style = bodyLargeMedium.copy(lineHeight = 22.4.sp),
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Preview
@Composable
fun SubscribeToPremiumScreenPreview() {
    SubscribeToPremiumScreen({}, {})
}

@Preview
@Composable
fun SubscriptionTermsPreview() {
    SubscriptionTerms(stringId = R.string.subscribe_to_premium_subscription_terms_1)
}