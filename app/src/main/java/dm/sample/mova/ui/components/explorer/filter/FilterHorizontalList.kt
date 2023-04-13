package dm.sample.mova.ui.components.explorer.filter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dm.sample.mova.domain.entities.Filter
import dm.sample.mova.ui.components.SelectableButton

@Composable
fun FilterHorizontalList(filters: List<Filter>, onRemove: (Filter) -> Unit) {
    val data = filters.filter { !it.isDefaultSelected }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) {
        items(data) { item ->
            SelectableButton(
                buttonText = item.text,
                isSelected = true,
                onClick = {
                    if (!it) onRemove(item)
                },
            )
        }
    }
}
