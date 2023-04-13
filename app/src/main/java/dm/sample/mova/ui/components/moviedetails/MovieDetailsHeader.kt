package dm.sample.mova.ui.components.moviedetails

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.theme.Black
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.isImageDark
import dm.sample.mova.ui.utils.loadBitmapFromUrl

@Composable
fun MovieDetailsHeader(
    posterImageUrl: String,
    onBackClick: () -> Unit,
    onCastClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    var textColor by remember { mutableStateOf(Color.Transparent) }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = Unit) {
        bitmap= loadBitmapFromUrl(context, posterImageUrl)
        bitmap?.let {
            textColor = if (isImageDark(it, 0, 0, it.width, it.height / 2)) White else Black
        }
    }


    Box(
        modifier = modifier
            .height(320.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {

        if (isPreview) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Movie details poster",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            bitmap?.let {
                Image(
                    it.asImageBitmap(),
                    contentDescription = "Movie details poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        TopBarButtons(
            iconsColor = textColor,
            onBackClick = onBackClick,
            onCastClick = onCastClick,
            modifier = Modifier.align(Alignment.TopStart)
        )
    }
}

@Composable
private fun TopBarButtons(
    iconsColor: Color,
    onBackClick: () -> Unit,
    onCastClick: () -> Unit,
    modifier: Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(horizontal = 29.dp, vertical = 40.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        IconButton(
            onClick = onBackClick,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_left),
                tint = iconsColor,
                contentDescription = "Back button"
            )
        }
        IconButton(
            onClick = onCastClick,
            interactionSource = remember { NoRippleInteractionSource() }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_cast),
                tint = iconsColor,
                contentDescription = "Cast button"
            )
        }
    }
}