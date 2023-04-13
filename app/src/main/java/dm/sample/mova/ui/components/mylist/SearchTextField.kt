package dm.sample.mova.ui.components.mylist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dm.sample.mova.R
import dm.sample.mova.ui.components.SampleTextField

private const val SEARCH_REGEX = "[^A-Za-z0-9\$&+,:;\\\\?@#|/'<>. ^*()%!-]"

@Composable
fun MyListSearchTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val filterRegex = Regex(SEARCH_REGEX)
    SampleTextField(
        value = value,
        onValueChange = { if (it.contains(filterRegex).not()) onValueChanged(it) },
        placeholderId = R.string.my_list_search,
        leadingIcon = R.drawable.ic_search,
        trailingIcon = R.drawable.ic_close,
        trailingIconClick = onCloseClick,
        modifier = modifier
    )
}
