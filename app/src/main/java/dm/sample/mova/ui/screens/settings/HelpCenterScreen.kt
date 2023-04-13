@file:OptIn(ExperimentalPagerApi::class)

package dm.sample.mova.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Faq
import dm.sample.mova.domain.entities.FaqCategory
import dm.sample.mova.ui.components.FilterButton
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaDialog
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.SampleTextField
import dm.sample.mova.ui.components.settings.helpcenter.FaqItem
import dm.sample.mova.ui.components.settings.helpcenter.HelpCenterContactUs
import dm.sample.mova.ui.components.settings.helpcenter.HelpCenterPager
import dm.sample.mova.ui.components.settings.helpcenter.HelpCenterTabs
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.viewmodel.settings.HelpViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

private const val SEARCH_REGEX = "[^A-Za-z0-9\$&+,:;=\\\\?@#|/'<>.^* ()%!-]"

@Composable
fun HelpCenterScreen(
    viewModel: HelpViewModel = hiltViewModel(),
    navigateOnBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState()
    when {
        uiState.isLoading -> {
            MovaLoadingScreen()
        }
        else -> {
            HelpCenterContent(
                selectedFaqCategories = uiState.selectedFaqCategories,
                faqCategories = uiState.faqCategories,
                navigateOnBack = navigateOnBack,
                onSelectFaqCategory = viewModel::selectFaqCategory,
                faqs = uiState.faqs,
                onContactClick = viewModel::onContactClick,
                searchText = uiState.searchText,
                onSearchTextChange = viewModel::onSearchTextChange,
                onClickOption = {},
                onClickFilter = {},
                pagerState = pagerState,
            )
        }
    }

    if (uiState.isServiceUnavailable) {
        MovaDialog(
            onButtonClick = { viewModel.dismissDialog() },
            onDismiss = { viewModel.dismissDialog() },
            title = R.string.sorry_text,
            text = R.string.unavailable_service_text,
            icon = R.drawable.ic_dialog_ok,
            hasButton = true
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetch()
    }
}

@Composable
private fun HelpCenterContent(
    selectedFaqCategories: List<FaqCategory>,
    faqs: List<Faq>,
    faqCategories: List<FaqCategory>,
    navigateOnBack: () -> Unit,
    onSelectFaqCategory: (FaqCategory) -> Unit,
    onContactClick: () -> Unit,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClickOption: () -> Unit,
    onClickFilter: () -> Unit,
    pagerState : PagerState,
) {
    val scrollState = rememberScrollState()

    val tabs = listOf(
        stringResource(R.string.settings_help_faq_tab),
        stringResource(R.string.settings_help_contact_us_tab)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {

            HeaderWithButtonAndTitle(
                title = R.string.settings_help_title,
                onBackClick = navigateOnBack,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onClickOption,
                interactionSource = remember { NoRippleInteractionSource() },
                modifier = Modifier.padding(end = 14.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_options),
                    contentDescription = "Help Center header option",
                )
            }
        }

        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            HelpCenterTabs(
                tabs = tabs,
                pagerState = pagerState,
            )

            HelpCenterPager(
                pageCount = tabs.size,
                pagerState = pagerState,
                faqPage = {
                    HelpCenterFAQ(
                        selectedCategories = selectedFaqCategories,
                        categories = faqCategories,
                        onCategorySelect = onSelectFaqCategory,
                        faqs = faqs,
                        searchText = searchText,
                        onSearchTextChange = onSearchTextChange,
                        onClickFilter = onClickFilter,
                    )
                },
                contactUsPage = {
                    HelpCenterContactUs(
                        onContactClick = onContactClick
                    )
                }
            )
        }
    }
}

@Composable
private fun HelpCenterFilters(
    categories: List<FaqCategory>,
    selectedCategories: List<FaqCategory>,
    onCategorySelect: (FaqCategory) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(all = 24.dp)
    ) {
        items(categories.size) { index ->
            val cat = categories[index]
            val isCatSelected = selectedCategories.contains(cat)
            FilterButton(
                filterName = cat.name,
                isSelected = isCatSelected,
                onClick = { onCategorySelect(cat) },
                modifier = Modifier.padding(end = 12.dp)
            )
        }
    }

}

@Composable
private fun HelpCenterFAQ(
    selectedCategories: List<FaqCategory>,
    categories: List<FaqCategory>,
    onCategorySelect: (FaqCategory) -> Unit,
    faqs: List<Faq>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClickFilter: () -> Unit,
) {

    Column { // TODO(Lazy Column)
        HelpCenterFilters(
            categories = categories,
            selectedCategories = selectedCategories,
            onCategorySelect = onCategorySelect
        )
        HelpCenterSearchField(
            searchText = searchText,
            onSearchTextChange = onSearchTextChange,
            onClickFilter = onClickFilter,
        )
        MovaVerticalSpacer(height = 12.dp)

        faqs.forEach {
            FaqItem(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                question = it.question,
                answer = it.answer,
            )
        }
    }
}

@Composable
private fun HelpCenterSearchField(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onClickFilter: () -> Unit,
) {
    val filterRegex = Regex(SEARCH_REGEX)
    val focusRequester = remember { FocusRequester() }

    Row(
        Modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {

        SampleTextField(
            value = searchText,
            onValueChange = { if (it.contains(filterRegex).not()) onSearchTextChange(it) },
            placeholderId = R.string.my_list_search,
            leadingIcon = R.drawable.ic_search,
            focusRequester = focusRequester,
            onClick = { focusRequester.requestFocus() },
            trailingIcon = R.drawable.ic_filter_outlined,
            trailingIconTint = Primary500,
            trailingIconClick = onClickFilter,
            modifier = Modifier.weight(1f),
        )
    }
}

@ShowkaseComposable(
    name = "Help Center Screen FAQ",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewHelpCenterFAQ() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        HelpCenterContent(
            selectedFaqCategories = listOf(),
            faqCategories = listOf(
                FaqCategory(1, "General"),
                FaqCategory(2, "Account"),
                FaqCategory(3, "Video"),
            ),
            faqs = listOf(
                Faq(
                    question = "What is Mova?",
                    answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    category = listOf(FaqCategory(3, "Video"))
                )
            ),
            searchText = "",
            onContactClick = {},
            onSearchTextChange = {},
            navigateOnBack = {},
            onSelectFaqCategory = {},
            onClickFilter = {},
            onClickOption = {},
            pagerState = rememberPagerState(initialPage = 0)
        )
    }
}



@ShowkaseComposable(
    name = "Help Center Screen Contacts",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewHelpCenterContacts() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        HelpCenterContent(
            selectedFaqCategories = listOf(),
            faqCategories = listOf(
                FaqCategory(1, "General"),
                FaqCategory(2, "Account"),
                FaqCategory(3, "Video"),
            ),
            faqs = listOf(
                Faq(
                    question = "What is Mova?",
                    answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    category = listOf(FaqCategory(3, "Video"))
                )
            ),
            searchText = "",
            onContactClick = {},
            onSearchTextChange = {},
            navigateOnBack = {},
            onSelectFaqCategory = {},
            pagerState = rememberPagerState(initialPage = 1),
            onClickFilter = {},
            onClickOption = {},
        )
    }
}