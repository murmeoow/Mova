package dm.sample.mova.ui.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.settings.LanguageSelectList
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.viewmodel.settings.LanguageViewModel
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import java.util.*

@Composable
fun LanguageSettingsScreen(
    viewModel: LanguageViewModel = hiltViewModel(),
    navigateOnBack: () -> Unit,
) {

    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val languages = remember { viewModel.getLanguages() }
    val suggestedLanguages = remember { viewModel.getSuggestedLanguages() }

    LanguageSettingsContent(
        languages = languages,
        selectedLanguage = selectedLanguage,
        suggestedLanguages = suggestedLanguages,
        onLanguageSelect = viewModel::selectLanguage,
        navigateOnBack = navigateOnBack,
    )
}

@Composable
private fun LanguageSettingsContent(
    languages: List<Locale>,
    selectedLanguage: Locale,
    suggestedLanguages: List<Locale>,
    onLanguageSelect: (Locale) -> Unit,
    navigateOnBack: () -> Unit,
) {

    Column(Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)) {
        HeaderWithButtonAndTitle(
            title = R.string.settings_language_title,
            onBackClick = navigateOnBack,
        )

        LanguageSelectList(
            languages = languages,
            suggestedLanguages = suggestedLanguages,
            selectedLanguage = selectedLanguage,
            onLanguageSelect = onLanguageSelect,
        )
    }
}


@ShowkaseComposable(
    name = "Language Settings Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewLanguageSettingsScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        LanguageSettingsContent(
            navigateOnBack = { /* no-op */ },
            languages = Locale.getAvailableLocales().toList(),
            selectedLanguage = Locale.getDefault(),
            suggestedLanguages = listOf(
                Locale.UK,
                Locale.US,
                Locale.ITALIAN,
            ),
            onLanguageSelect = {

            }
        )
    }
}