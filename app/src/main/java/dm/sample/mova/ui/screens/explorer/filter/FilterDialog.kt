package dm.sample.mova.ui.screens.explorer.filter

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.domain.entities.FilterCategory
import dm.sample.mova.domain.entities.FilterType
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaTransparentRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.theme.Dark2
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Error
import dm.sample.mova.ui.theme.Grayscale300
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun FilterDialog(
    filters: List<FilterCategory>?,
    selectedFilters: List<Filter>,
    onApplyClick: () -> Unit,
    onResetClick: () -> Unit,
    onAddFilter: (Filter) -> Unit,
    onRemoveFilter: (Filter) -> Unit,
    dismissDialog: () -> Unit
) {

    filters?.let { filterCategory ->
        FilterContent(
            filterCategories = filterCategory,
            selectedFilters = selectedFilters,
            dismissDialog = dismissDialog,
            onResetFilters = onResetClick,
            onApplyClick = onApplyClick,
            onRemoveFilter = onRemoveFilter,
            onAddFilter = onAddFilter
        )
    }

}

@Composable
private fun FilterContent(
    filterCategories: List<FilterCategory>,
    selectedFilters: List<Filter>,
    onResetFilters: () -> Unit,
    onApplyClick: () -> Unit,
    onAddFilter: (Filter) -> Unit,
    onRemoveFilter: (Filter) -> Unit,
    dismissDialog: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme()) Dark2 else White)
            .verticalScroll(scrollState, true)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp, 12.dp)
                .padding(top = 8.dp)
                .background(Grayscale300, shape = RoundedCornerShape(10.dp)),
        )

        MovaVerticalSpacer(height = 24.dp)
        Text(
            text = stringResource(R.string.sort_and_filter),
            style = MaterialTheme.typography.h4,
            color = Error
        )
        MovaVerticalSpacer(height = 24.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        filterCategories.forEach {
            MovaVerticalSpacer(height = 24.dp)
            FilterCategory(
                name = it.text,
                filterItems = it.filters,
                onItemClick = { filter, isSelected ->
                    if (isSelected) onAddFilter(filter) else onRemoveFilter(filter)
                },
                selectedFilterItems = selectedFilters
            )
        }
        MovaVerticalSpacer(height = 24.dp)
        Divider(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        MovaVerticalSpacer(height = 24.dp)
        DialogBottomButtons(
            onResetClick = {
                onResetFilters()
                dismissDialog()
            },
            onApplyClick = {
                onApplyClick()
                dismissDialog()
            },
        )
        MovaVerticalSpacer(height = 24.dp)
    }
}

@Composable
private fun DialogBottomButtons(
    onResetClick: () -> Unit,
    onApplyClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        MovaTransparentRedButton(
            buttonText = R.string.reset,
            onClick = onResetClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(Modifier.width(12.dp))
        MovaRedButton(
            buttonText = R.string.apply,
            onClick = onApplyClick,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}


@ShowkaseComposable(
    name = "Explore Filter Screen Dialog",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewExploreFilterScreenDialog() {
    MovaTheme(
        darkTheme = isSystemInDarkTheme(),
    ) {
        val filterItems = listOf("Action", "Comedy", "Romance", "Horror").map {
            Filter(text = it, value = "", isDefaultSelected = false, filterType = FilterType.GENRE)
        }
        val filterCategory = FilterCategory("Genres", filterItems)
        FilterContent(
            listOf(filterCategory),
            onResetFilters = {},
            onApplyClick = {},
            dismissDialog = {},
            onAddFilter = {},
            onRemoveFilter = {},
            selectedFilters = listOf(),
        )
    }
}