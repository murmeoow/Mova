package dm.sample.mova.ui.components.mylist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Genre
import dm.sample.mova.ui.components.FilterButton

@Composable
fun MyListFilters(
    filtersList: List<Genre>,
    selectedFilters: List<Long>,
    onFilterClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyRowState = rememberLazyListState()
    val filters = filtersList.toMutableList()
    filters.add(
        index = 0,
        element = Genre(id = 0, name = stringResource(id = R.string.my_list_all_filter))
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 24.dp),
        state = lazyRowState,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(filters.size) { index ->
            val isSelected = selectedFilters.contains(filters[index].id.toLong())
            FilterButton(
                filterName = filters[index].name,
                isSelected = isSelected,
                onClick = { onFilterClick(filters[index].id.toLong()) }
            )
        }
    }
}
