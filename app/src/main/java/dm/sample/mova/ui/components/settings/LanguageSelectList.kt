package dm.sample.mova.ui.components.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dm.sample.mova.R
import dm.sample.mova.ui.components.settings.LanguageItem
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LanguageSelectList(
    languages: List<Locale>,
    suggestedLanguages: List<Locale>,
    selectedLanguage: Locale,
    onLanguageSelect: (Locale) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        stickyHeader {
            Text(
                text = stringResource(R.string.settings_language_suggested),
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(start = 24.dp, bottom = 24.dp, top = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )
        }
        items(suggestedLanguages) { lang ->
            LanguageItem(
                isSelected = selectedLanguage == lang,
                title = lang.displayName,
                onSelect = { onLanguageSelect(lang) }
            )
        }
        item {
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 4.dp),
            )
        }
        stickyHeader {
            Text(
                text = stringResource(R.string.settings_language),
                style = MaterialTheme.typography.h5.copy(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(start = 24.dp, bottom = 24.dp, top = 24.dp),
                color = MaterialTheme.colors.onBackground,
            )
        }
        items(languages) { lang ->
            LanguageItem(
                isSelected = selectedLanguage == lang,
                title = lang.displayName,
                onSelect = { onLanguageSelect(lang) }
            )
        }
    }
}