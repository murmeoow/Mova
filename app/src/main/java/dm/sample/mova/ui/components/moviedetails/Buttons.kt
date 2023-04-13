package dm.sample.mova.ui.components.moviedetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeSemiBold

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary
        ),
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle_play),
            contentDescription = "play icon",
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(9.dp))
        Text(
            text = stringResource(id = R.string.play),
            style = bodyLargeSemiBold.copy(lineHeight = 22.4.sp),
            color = White
        )
    }
}

@Composable
fun DownloadButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        border = BorderStroke(2.dp, Primary500),
        shape = RoundedCornerShape(100.dp),
        contentPadding = PaddingValues(vertical = 10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            disabledBackgroundColor = MaterialTheme.colors.background
        ),
        enabled = isEnabled,
        interactionSource = remember { NoRippleInteractionSource() },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_download_red),
            contentDescription = "download icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(18.dp),
        )
        Spacer(Modifier.width(9.dp))
        Text(
            text = stringResource(id = R.string.profile_download),
            style = bodyLargeSemiBold.copy(lineHeight = 22.4.sp),
            color = Primary500
        )
    }
}