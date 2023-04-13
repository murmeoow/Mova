package dm.sample.mova.ui.components.settings.helpcenter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@ExperimentalPagerApi
@Composable
fun HelpCenterPager(
    pageCount: Int,
    pagerState: PagerState,
    faqPage: @Composable () -> Unit,
    contactUsPage: @Composable () -> Unit,
) {
    HorizontalPager(
        count = pageCount,
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top,
    ) { index ->
        if (index == 0) faqPage() else contactUsPage()
    }
}