package dm.sample.mova.ui.components.createaccount

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.utils.isDarkTheme
import coil.compose.rememberAsyncImagePainter
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun AvatarIcon(
    avatarImagePath: String? = null,
    onAvatarEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val avatarRes = if (isDarkTheme()) R.drawable.avatar_night else R.drawable.avatar
    Box(
        modifier = modifier
            .width(120.dp)
            .height(120.dp)
            .clickable(
                onClick = onAvatarEdit,
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
    ) {
        if (avatarImagePath == null) {
            Image(
                painter = painterResource(avatarRes),
                contentDescription = "profile avatar",
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = Uri.parse(avatarImagePath)),
                contentDescription = "profile avatar",
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .clip(CircleShape)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_edit),
            tint = Color.Unspecified,
            contentDescription = "edit",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 3.dp, bottom = 3.dp)
        )
    }

}

@ShowkaseComposable(
    name = "Avatar Icon",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AvatarIconPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        AvatarIcon(
            onAvatarEdit = {

            },
        )
    }
}