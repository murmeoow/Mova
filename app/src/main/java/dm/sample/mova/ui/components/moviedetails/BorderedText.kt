package dm.sample.mova.ui.components.moviedetails

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.bodyXSmallSemiBold
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun BorderedText(
    text: String
) {
    Box(
        modifier = Modifier
        .border(
            width = 0.5.dp,
            color = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(6.dp)
        )
    ) {
        Text(
            text = text,
            style = bodyXSmallSemiBold.copy(lineHeight = 12.sp),
            color = MaterialTheme.colors.primary,
            maxLines = 1,
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
        )
    }

}

@ShowkaseComposable(
    name = "Bordered Text Preview",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun BorderedTextPreview() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        BorderedText("Thriller")
    }
}