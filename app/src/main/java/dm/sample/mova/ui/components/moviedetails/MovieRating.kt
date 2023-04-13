package dm.sample.mova.ui.components.moviedetails

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodySmallSemiBold
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun MovieRating(
    rating: Float,
    onRateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .wrapContentWidth()
            .clickable(
                onClick = { onRateClick() },
                indication = null,
                interactionSource = remember { NoRippleInteractionSource() }
            )
    ) {
        RatingStar(
            value = rating / 10,
            size = 20
        )
        Text(
            text = "%.1f".format(rating),
            style = bodySmallSemiBold,
            color = Primary500
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_rating),
            tint = Primary500,
            contentDescription = "Rate button"
        )
    }
}

@Composable
fun RatingStar(
    value: Float,
    size: Int,
) {
    val context = LocalContext.current
    var icon: Int? = null
    var iconFilled: Int? = null
    when (size) {
        10 -> {
            icon = R.drawable.ic_star_transparent_10
            iconFilled = R.drawable.ic_star_filled_10
        }
        20 -> {
            icon = R.drawable.ic_star_transparent_20
            iconFilled = R.drawable.ic_star_filled_20
        }
    }

    val image = remember { getBitmapFromImage(context, icon!!) }
    val imageFilled = remember { getBitmapFromImage(context, iconFilled!!) }

    Canvas(modifier = Modifier.size(size.dp)) {
        drawRating(
            rating = value,
            image = image.asImageBitmap(),
            imageFull = imageFilled.asImageBitmap()
        )
    }
}

private fun DrawScope.drawRating(
    rating: Float,
    image: ImageBitmap,
    imageFull: ImageBitmap,
) {
    val imageWidth = image.width.toFloat()
    val imageHeight = image.height.toFloat()

    drawImage(
        image = image,
        topLeft = Offset(0f, 0f)
    )

    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)

        drawImage(
            image = imageFull,
            topLeft = Offset(0f, 0f)
        )
        val start = rating * imageWidth
        val size = imageWidth - start

        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(size, height = imageHeight),
            blendMode = BlendMode.SrcIn
        )
        restoreToCount(checkPoint)
    }
}

private fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {
    val db = ContextCompat.getDrawable(context, drawable)
    val bit = Bitmap.createBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bit)
    db.setBounds(0, 0, canvas.width, canvas.height)
    db.draw(canvas)
    return bit
}

@Preview
@Composable
fun RatingStarPreview() {
    RatingStar(0.7f, 20)
}

@ShowkaseComposable(
    name = "Movie rating",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun MovieRatingPreview() {
    MovieRating(
        5.8f, {}
    )
}