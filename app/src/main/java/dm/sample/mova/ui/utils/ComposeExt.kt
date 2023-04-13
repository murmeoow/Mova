@file:OptIn(ExperimentalPagerApi::class)

package dm.sample.mova.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.semantics.Role
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import dm.sample.mova.ui.theme.Grayscale300
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.movaShimmer(
    customShimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View),
    background: Color = Grayscale300,
) : Modifier {
    return shimmer(customShimmer).background(background)
}

@Composable
fun LazyGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}


@Composable
fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@Composable
fun isDarkTheme(): Boolean{
    val isPreview = LocalInspectionMode.current
    return if (isPreview) isSystemInDarkTheme()
    else !MaterialTheme.colors.isLight
}

fun PagerState.isLastPage() = currentPage == pageCount - 1


fun isImageDark(bitmap: Bitmap, left: Int, top: Int, right: Int, bottom: Int): Boolean {
    val palette = Palette.from(bitmap).setRegion(left, top, right, bottom).generate()
    val mostPopulous = getMostPopulousSwatch(palette) ?: return true
    return ColorUtils.calculateLuminance(mostPopulous.rgb) < 0.5
}

private fun getMostPopulousSwatch(palette: Palette?): Palette.Swatch? {
    return palette?.swatches?.maxBy { it.population }
}

suspend fun loadBitmapFromUrl(context: Context, imageUrl: String): Bitmap {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(false)
        .build()

    val result = (loader.execute(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = MutableInteractionSource()
) = composed {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { interactionSource }
    )
}