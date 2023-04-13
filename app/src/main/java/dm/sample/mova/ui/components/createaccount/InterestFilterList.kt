package dm.sample.mova.ui.components.createaccount

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyXLargeBold
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun InterestList(
    interestList: List<Genre>,
    modifier: Modifier = Modifier
) {
    FlowRow(modifier = modifier
        .padding(bottom = 8.dp)
        .verticalScroll(rememberScrollState())
    ) {
        for (filter in interestList) {
            GenreItem(genreName = filter.name)
        }
    }
}

@Composable
fun GenreItem(
    genreName: String,
) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor = if (isSelected) Primary500 else MaterialTheme.colors.background
    val textColor = if (isSelected) White else Primary500
    Button(
        onClick = { isSelected = isSelected.not() },
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        interactionSource = NoRippleInteractionSource(),
        border = BorderStroke(width = 2.dp, Primary500),
        elevation = ButtonDefaults.elevation(0.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp),
        modifier = Modifier.padding(end = 6.dp, top = 6.dp, bottom = 6.dp)
    ) {
        Text(
            text = genreName,
            style = bodyXLargeBold.copy(
                lineHeight = 25.2.sp),
            color = textColor
        )
    }
}

@ShowkaseComposable(
    name = "Filter Item",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun FilterItemPreview() {
    GenreItem(genreName = "Action")
}

@ShowkaseComposable(
    name = "Interest Filters List",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Composable
fun InterestFiltersListPreview() {
    InterestList(interestList = listOf(
        Genre(1, "Action"),
        Genre(1, "Thriller"),
        Genre(1, "Animation"),
        Genre(1, "Superheroes"),
    ))
}