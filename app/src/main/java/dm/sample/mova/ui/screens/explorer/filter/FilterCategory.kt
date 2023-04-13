package dm.sample.mova.ui.screens.explorer.filter

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.FilterType
import dm.sample.mova.ui.components.MovaHorizontalSpacer
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.SelectableButton
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale800
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun FilterCategory(
    name: String,
    filterItems: List<Filter>,
    selectedFilterItems: List<Filter>,
    onItemClick: (Filter, Boolean) -> Unit,
) {
    val onSelect: (Filter, Boolean) -> Unit = { filter, isSelected ->
        onItemClick(filter, isSelected)
    }

    Column {
        Text(
            text = name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            color = if (isDarkTheme()) White else Grayscale800,
        )
        MovaVerticalSpacer(24.dp)
        LazyRow(contentPadding = PaddingValues(horizontal = 24.dp)) {
            items(filterItems) { item ->
                val isSelected = selectedFilterItems.contains(item)
                SelectableButton(buttonText = item.text, isSelected = isSelected) {
                    onSelect(item, it)
                }
                MovaHorizontalSpacer(width = 4.dp)
            }
        }
    }
}


@ShowkaseComposable(
    name = "Explore Filter Category",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewExplorerFilterScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        Box(modifier = Modifier.background(MaterialTheme.colors.background)){
            MovaVerticalSpacer(height = 24.dp)
            val items = listOf("2012", "2013", "2014", "2015", "2016", "2017").map {
                Filter(text = it, value = "", isDefaultSelected = false, filterType = FilterType.TIME)
            }
            FilterCategory(name = "Time/Periods", items, items.take(1)) { _, _ ->

            }
            MovaVerticalSpacer(height = 24.dp)
        }
    }
}