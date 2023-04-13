package dm.sample.mova.ui.components.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.Grayscale700
import dm.sample.mova.ui.theme.bodyXSmallRegular
import dm.sample.mova.ui.theme.bodyXSmallSemiBold
import dm.sample.mova.ui.utils.isDarkTheme
import coil.compose.rememberAsyncImagePainter
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun CastCard(
    name: String,
    jobTitle: String,
    image: String?,
    modifier: Modifier = Modifier
) {
    val jobTitleColor = if (isDarkTheme()) Grayscale300 else Grayscale700
    Row(
       modifier = modifier.wrapContentWidth().height(40.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(image),
            contentDescription = "Cast image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)

        )
        Spacer(Modifier.width(12.dp))
        Column() {
            Text(
                text = name.replaceFirst(" ","\n"),
                style = bodyXSmallSemiBold.copy(lineHeight = 12.sp),
                color = MaterialTheme.colors.onBackground,
            )
            MovaVerticalSpacer(height = 4.dp)
            Text(
                text = jobTitle,
                style = bodyXSmallRegular.copy(lineHeight = 12.sp),
                color = jobTitleColor,
            )
        }

    }
}


@ShowkaseComposable(
    name = "Movie Cast Card Preview",
    group = Constants.SHOWKASE_GROUP_COMPONENTS
)
@Preview
@Composable
fun MovieCastCardPreview() {
    CastCard(
        name = "James Cameron",
        jobTitle = "Director",
        image = ""
    )
}