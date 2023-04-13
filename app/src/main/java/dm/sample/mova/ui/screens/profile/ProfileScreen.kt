package dm.sample.mova.ui.screens.profile

import android.Manifest
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.entities.Country
import dm.sample.mova.navigation.Screen
import dm.sample.mova.ui.components.MovaLoadingScreen
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.createaccount.AvatarIcon
import dm.sample.mova.ui.components.profile.GuestScreen
import dm.sample.mova.ui.components.profile.JoinPremiumContainer
import dm.sample.mova.ui.components.profile.LogoutDialog
import dm.sample.mova.ui.components.profile.ProfileHeader
import dm.sample.mova.ui.components.profile.ProfileOptions
import dm.sample.mova.ui.components.profile.edit.ProfileEditContent
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.bodyMediumMedium
import dm.sample.mova.ui.viewmodel.ThemeViewModel
import dm.sample.mova.ui.viewmodel.profile.ProfileViewModel
import dm.sample.mova.ui.viewmodel.profile.utils.isReadExternalStoragePermissionGranted
import dm.sample.mova.ui.viewmodel.profile.utils.isWriteExternalStoragePermissionGranted
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    onChangeBottomBarVisibility: (isVisible: Boolean) -> Unit,
    navigateTo: (Screen) -> Unit,
    navigateToStart: () -> Unit,
    onBackClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()


    LaunchedEffect(key1 = Unit) {
        viewModel.navEvent.collectLatest { event ->
            when (event) {
                is ProfileViewModel.NavEvent.StartScreen -> {
                    navigateToStart()
                }
            }
        }
    }

    // account selected country or first in list
    val selectedCountry by remember {
        derivedStateOf {
            val phoneCode = state.account.phoneCode ?: viewModel.countryList.first().code
            viewModel.countryList.first { it.code == phoneCode }
        }
    }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = viewModel::selectAvatar,
    )

    val requestPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { },
    )

    val context = LocalContext.current

    val onAvatarEdit: () -> Unit = {
        when {
            !isWriteExternalStoragePermissionGranted(context) -> {
                requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            !isReadExternalStoragePermissionGranted(context) -> {
                requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            else -> {
                picker.launch("image/*")
            }
        }
    }

    when {
        state.isLoading -> {
            MovaLoadingScreen()
        }
        state.isLoginAsGuest -> {
            GuestScreen(
                onClick = viewModel::onLogout
            )
        }
        else -> {
            ProfileScreenContent(
                onChangeBottomBarVisibility = onChangeBottomBarVisibility,
                isProfileEditMode = state.isProfileEditMode,
                onProfileEdit = viewModel::setEditMode,
                countryList = viewModel.countryList,
                selectedCountry = selectedCountry,
                onUpdate = viewModel::onProfileUpdate,
                account = state.account,
                hasSubscription = state.hasSubscription,
                onAvatarEdit = onAvatarEdit,
                onClickNotificationSettings = { navigateTo(Screen.NotificationSettings) },
                onClickHelpCenter = { navigateTo(Screen.HelpCenter) },
                onClickDownloadSettings = { navigateTo(Screen.DownloadSettings) },
                onClickSecuritySettings = { navigateTo(Screen.SecuritySettings) },
                onClickLanguageSettings = { navigateTo(Screen.LanguagesSettings) },
                onClickPrivacyPolicy = { navigateTo(Screen.PrivacyPolicySettings) },
                onClickProfileLogout = { viewModel.onLogout() },
                onClickPremium = { navigateTo(Screen.SubscribeToPremium) },
                onClickSubscription = {  },
                currentLanguageValue = state.currentLanguage.displayName,
                onThemeChange = themeViewModel::changeDarkTheme
            )
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchData()
    })

    LaunchedEffect(key1 = state.isProfileEditMode) {
        if (state.isProfileEditMode) {
            onChangeBottomBarVisibility(false)
        } else {
            onChangeBottomBarVisibility(true)
        }
    }

    BackHandler {
        if (state.isProfileEditMode) viewModel.setEditMode(false) else onBackClick()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileScreenContent(
    onChangeBottomBarVisibility: (isVisible: Boolean) -> Unit,
    isProfileEditMode: Boolean,
    onProfileEdit: (Boolean) -> Unit,
    countryList: List<Country>,
    currentLanguageValue: String,
    selectedCountry: Country,
    account: Account,
    hasSubscription: Boolean,
    onUpdate: (Account) -> Unit,
    onAvatarEdit: () -> Unit,
    onClickNotificationSettings: () -> Unit,
    onClickHelpCenter: () -> Unit,
    onClickDownloadSettings: () -> Unit,
    onClickSecuritySettings: () -> Unit,
    onClickLanguageSettings: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickProfileLogout: () -> Unit,
    onClickPremium: () -> Unit,
    onClickSubscription: () -> Unit,
    onThemeChange: (isDark: Boolean) -> Unit,
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val toggleBottomSheet = {
        coroutineScope.launch {
            if (bottomSheetState.isVisible) bottomSheetState.hide()
            else bottomSheetState.show()
        }
    }

    LaunchedEffect(key1 = bottomSheetState.targetValue) {
        when (bottomSheetState.targetValue) {
            ModalBottomSheetValue.Hidden -> {
                onChangeBottomBarVisibility(true)
            }
            ModalBottomSheetValue.Expanded,
            ModalBottomSheetValue.HalfExpanded,
            -> {
                onChangeBottomBarVisibility(false)
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            LogoutDialog(
                onClickLogout = {
                    toggleBottomSheet()
                    onClickProfileLogout()
                },
                onClickCancel = { toggleBottomSheet() }
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                val scrollState = rememberScrollState()
                MovaVerticalSpacer(height = 36.dp)

                ProfileHeader(
                    Modifier.padding(horizontal = 28.dp),
                    isEditMode = isProfileEditMode,
                    onBackClick = { onProfileEdit(false) },
                )
                Column(
                    modifier = Modifier.verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    MovaVerticalSpacer(height = 36.dp)

                    AvatarIcon(
                        onAvatarEdit = onAvatarEdit,
                        avatarImagePath = account.avatarPath
                    )

                    MovaVerticalSpacer(height = 12.dp)

                    AnimatedVisibility(visible = isProfileEditMode) {
                        ProfileEditContent(
                            countryList = countryList,
                            defaultPhoneCountry = selectedCountry,
                            onUpdate = onUpdate,
                            account = account
                        )
                    }

                    AnimatedVisibility(visible = !isProfileEditMode) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = account.nickname ?: "",
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.onBackground,
                            )

                            MovaVerticalSpacer(height = 8.dp)

                            Text(
                                text = account.email ?: "",
                                style = bodyMediumMedium,
                                color = MaterialTheme.colors.onBackground,
                            )

                            MovaVerticalSpacer(height = 24.dp)

                            if (hasSubscription.not()) JoinPremiumContainer(
                                onClick = onClickPremium,
                                modifier = Modifier.padding(horizontal = 24.dp)
                            )

                            ProfileOptions(
                                currentLanguageValue = currentLanguageValue,
                                hasSubscription = hasSubscription,
                                onProfileEdit = { onProfileEdit(true) },
                                onClickNotificationOption = onClickNotificationSettings,
                                onClickHelpCenterOption = onClickHelpCenter,
                                onClickDownloadOption = onClickDownloadSettings,
                                onClickSecurityOption = onClickSecuritySettings,
                                onClickLanguageOption = onClickLanguageSettings,
                                onClickPrivacyPolicyOption = onClickPrivacyPolicy,
                                onClickProfileLogoutOption = {
                                    toggleBottomSheet()
                                },
                                onClickSubscriptionOption = onClickSubscription,
                                onThemeChange = onThemeChange
                            )
                        }
                    }
                }

            }
        },
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),

        )
}


@ShowkaseComposable(
    name = "Profile Screen",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewProfileScreen() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ProfileScreenContent(
            isProfileEditMode = false,
            onProfileEdit = {

            },
            countryList = emptyList(),
            selectedCountry = Country("US", "+1", "USA"),
            onUpdate = {},
            account = Account(1,
                "Fullname",
                "Nickname",
                null,
                null,
                null,
                "nickname@mail.ru",
                null,
                null),
            hasSubscription = false,
            onAvatarEdit = {},
            onClickNotificationSettings = {},
            onClickDownloadSettings = {},
            onClickSecuritySettings = {},
            onClickLanguageSettings = {},
            currentLanguageValue = "English (US)",
            onClickPrivacyPolicy = {},
            onClickHelpCenter = {},
            onClickProfileLogout = {},
            onChangeBottomBarVisibility = {},
            onClickPremium = {},
            onThemeChange = {},
            onClickSubscription = {}
        )
    }
}


@ShowkaseComposable(
    name = "Profile Screen Edit Mode",
    group = Constants.SHOWKASE_GROUP_SCREENS,
)
@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewProfileScreenEditMode() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        ProfileScreenContent(
            isProfileEditMode = true,
            onProfileEdit = {

            },
            countryList = emptyList(),
            selectedCountry = Country("US", "+1", "USA"),
            onUpdate = {},
            account = Account(1, null, null, null, null, null, null, null, null),
            onAvatarEdit = {},
            hasSubscription = false,
            onClickNotificationSettings = {},
            onClickDownloadSettings = {},
            onClickSecuritySettings = {},
            onClickLanguageSettings = {},
            currentLanguageValue = "English (US)",
            onClickPrivacyPolicy = {},
            onClickHelpCenter = {},
            onClickProfileLogout = {},
            onChangeBottomBarVisibility = {},
            onClickPremium = {},
            onThemeChange = {},
            onClickSubscription = {}
        )
    }
}