package dm.sample.mova.ui.components.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dm.sample.mova.ui.viewmodel.moviedetails.models.CastUiModel
import dm.sample.mova.ui.components.moviedetails.CastCard

@Composable
fun CastList(
    castList: List<CastUiModel>,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 24.dp),
        state = state,
        modifier = modifier.background(MaterialTheme.colors.background)
    ) {

        items(castList.size) { index ->
            CastCard(
                name = castList[index].name,
                jobTitle = castList[index].jobTitle,
                image = castList[index].image
            )
        }
    }
}