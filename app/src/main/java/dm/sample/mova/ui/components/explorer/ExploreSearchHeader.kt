package dm.sample.mova.ui.components.explorer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.ui.components.SampleTextField
import dm.sample.mova.ui.components.explorer.filter.FilterHeaderButton

private const val SEARCH_REGEX = "[^A-Za-z0-9\$&+,:;=\\\\?@#|/' <>.^*()%!-]"

@Composable
fun ExploreSearchHeader(
    searchText: String,
    isEnabled: Boolean,
    onSearchTextChanged: (String) -> Unit,
    onFilterClick: () -> Unit,
    onClear: () -> Unit,
    isClearIconVisible: Boolean,
) {
    val filterRegex = Regex(SEARCH_REGEX)
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        Modifier
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SampleTextField(
            value = searchText,
            onValueChange = { if (it.contains(filterRegex).not()) onSearchTextChanged(it) },
            placeholderId = R.string.my_list_search,
            leadingIcon = R.drawable.ic_search,
            trailingIcon = if (isClearIconVisible) R.drawable.ic_close else null,
            focusRequester = focusRequester,
            onClick = { focusRequester.requestFocus() },
            trailingIconClick = {
                onClear()
                focusManager.clearFocus()
            },
            modifier = Modifier
                .weight(1f)
                .height(56.dp)
        )
        Spacer(Modifier.width(12.dp))
        FilterHeaderButton(isEnabled = isEnabled, onClick = onFilterClick)
    }
}