package dm.sample.mova.ui.components.settings.helpcenter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.Grayscale500
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.bodyXLargeSemiBold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun HelpCenterTabs(
    tabs: List<String>,
    pagerState: PagerState,
) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.background,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions),
                color = Primary500,
                height = 4.dp
            )
        },
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        tabs.forEachIndexed { index, item ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(text = item,
                        style = bodyXLargeSemiBold.copy(lineHeight = 25.2.sp)
                    )
                },
                selectedContentColor = Primary500,
                unselectedContentColor = Grayscale500,
                interactionSource = remember { NoRippleInteractionSource() }
            )
        }
    }
}

