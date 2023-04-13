package dm.sample.mova.ui.screens.moviedetails.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.MovaTwoButtons
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.moviedetails.RatingStar
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.Grayscale700
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyXSmallMedium
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun RatingDialog(
    averageRating: Float,
    currentRating: Int?,
    usersCount: Long,
    onCancelClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onRatingClick: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp, 12.dp)
                .padding(top = 8.dp)
                .background(Grayscale300, shape = RoundedCornerShape(10.dp)),
        )
        MovaVerticalSpacer(height = 24.dp)
        Text(
            text = stringResource(R.string.rating_title),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        MovaVerticalSpacer(height = 22.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MovaVerticalSpacer(height = 18.dp)
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    text = "%.1f".format(averageRating),
                    style = MaterialTheme.typography.h1.copy(lineHeight = 57.6.sp),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.rating_max),
                    style = MaterialTheme.typography.h5.copy(lineHeight = 24.sp),
                    color = if (isSystemInDarkTheme()) Grayscale300 else Grayscale700,
                    modifier = Modifier.alignByBaseline()
                )
            }
            MovaVerticalSpacer(height = 8.dp)
            AverageStarRating(averageRating = averageRating)
            MovaVerticalSpacer(height = 10.dp)
            Text(
                text = stringResource(id = R.string.rating_users_count, usersCount.toString()),
                style = bodyXSmallMedium.copy(lineHeight = 12.sp),
                color = if (isSystemInDarkTheme()) Grayscale300 else Grayscale700
            )
        }
        MovaVerticalSpacer(height = 24.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
        )
        MovaVerticalSpacer(height = 24.dp)
        RateWithStar(
            rating = currentRating,
            onClick = onRatingClick
        )
        MovaVerticalSpacer(height = 24.dp)
        MovaTwoButtons(
            firstText = R.string.cancel_text,
            secondText = R.string.submit_text,
            onFirstClick = onCancelClick,
            onSecondClick = onSubmitClick
        )
        MovaVerticalSpacer(height = 22.dp)
    }
}

@Composable
private fun AverageStarRating(
    averageRating: Float,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.wrapContentWidth()
    ) {
        repeat(10) { rate ->
            if (averageRating.toInt() == rate) {
                RatingStar(
                    value = averageRating - averageRating.toInt(),
                    size = 10
                )
            } else {
                Icon(
                    painter = painterResource(
                        id =
                        if (averageRating.toInt() > rate) {
                            R.drawable.ic_star_filled
                        } else {
                            R.drawable.ic_star_transparent_10
                        }
                    ),
                    tint = Color.Unspecified,
                    contentDescription = "Rating star",
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}

@Composable
private fun RateWithStar(
    rating: Int?,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSelected by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(9.dp),
        modifier = modifier.wrapContentSize()
    ) {
        repeat(10) { index ->
            val rate = index + 1
            SimpleStarRating(
                text = rate,
                isSelected = if (rating != null) rating >= rate else false,
                onClick = { onClick(rate) },
                modifier = Modifier
                    .clickable(
                        onClick = {
                            isSelected = isSelected.not()
                            onClick(rate)
                        },
                        indication = null,
                        interactionSource = remember { NoRippleInteractionSource() }
                    ),
            )
        }
    }
}

@Composable
private fun SimpleStarRating(
    text: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = if (isSelected) R.drawable.ic_star_filled else R.drawable.ic_star
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(25.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = Primary500,
            contentDescription = "Rating star",
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = text.toString(),
            style = MaterialTheme.typography.h1.copy(fontSize = 8.sp),
            color = if (isSelected) White else Primary500
        )
    }
}

@ShowkaseComposable(
    name = "Rating Dialog",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun RatingDialogPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        RatingDialog(
            averageRating = 6.9f,
            currentRating = 3,
            usersCount = 5268,
            onCancelClick = {  },
            onSubmitClick = { },
            onRatingClick = {}
        )
    }
}

