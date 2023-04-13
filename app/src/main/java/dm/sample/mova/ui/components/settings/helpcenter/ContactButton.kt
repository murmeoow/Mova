package dm.sample.mova.ui.components.settings.helpcenter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun ContactButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = remember { NoRippleInteractionSource() }
        ),
        backgroundColor = if (isDarkTheme()) Dark2 else White,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp),
        ) {
            Icon(
                painter = icon,
                contentDescription = "contact icon",
                modifier = Modifier.size(20.dp),
                tint = Primary500,
            )

            Text(
                text = text,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(start = 18.dp)
            )
        }
    }
}

@ShowkaseComposable(
    name = "Contact Button",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun PreviewContactButton() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ContactButton(
            modifier = Modifier.padding(all = 24.dp),
            text = "Customer Service",
            icon = painterResource(R.drawable.ic_support),
            onClick = {

            }
        )
    }
}