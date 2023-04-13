package dm.sample.mova.ui.components.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContinueWithSocial(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .semantics { testTagsAsResourceId = true }
    ) {
        ContinueWithSocialButton(
            iconId = R.drawable.ic_facebook,
            onClick = onClick,
            modifier = Modifier.testTag("btnFacebook")
        )
        Spacer(modifier = Modifier.width(18.dp))
        ContinueWithSocialButton(
            iconId = R.drawable.ic_google,
            onClick = onClick,
            modifier = Modifier.testTag("btnGoogle")
        )
        Spacer(modifier = Modifier.width(18.dp))
        ContinueWithSocialButton(
            iconId = R.drawable.ic_apple,
            onClick = onClick,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.testTag("btnApple")
        )
    }
}

@Composable
fun ContinueWithSocialButton(
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    val backgroundColor = if (isDarkTheme()) Dark2 else White
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.secondary),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = modifier
            .height(56.dp)
            .width(80.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            tint = tint,
            contentDescription = ""
        )
    }
}

@ShowkaseComposable(
    name = "Continue With Social",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun ContinueWithSocialPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ContinueWithSocial({})
    }
}