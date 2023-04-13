package dm.sample.mova.ui.components.settings.helpcenter

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyMediumMedium
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun FaqItem(
    modifier: Modifier = Modifier,
    question: String,
    answer: String,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val isDarkTheme = isDarkTheme()
    val backgroundColor = if (isDarkTheme) Dark2 else White
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.fillMaxWidth(),
        backgroundColor = backgroundColor,
    ) {
        Column(
            modifier = Modifier.clickable(
                onClick = { isExpanded = isExpanded.not() },
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(backgroundColor)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(horizontal = 24.dp),
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = "expandable arrow",
                    tint = Primary500,
                    modifier = Modifier.size(14.dp),
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.background(backgroundColor)
                ) {
                    Divider(
                        color = MaterialTheme.colors.secondary,
                        modifier = modifier.fillMaxWidth(),
                    )

                    Text(
                        text = answer,
                        style = bodyMediumMedium,
                        modifier = Modifier.padding(horizontal = 24.dp),
                    )

                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(if (isExpanded) 16.dp else 24.dp)
                .background(backgroundColor)
            )

        }
    }
}


@ShowkaseComposable(
    name = "FAQ item",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewFaqItem() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        FaqItem(
            modifier = Modifier.padding(all = 24.dp),
            question = "What is Mova?",
            answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        )
    }
}