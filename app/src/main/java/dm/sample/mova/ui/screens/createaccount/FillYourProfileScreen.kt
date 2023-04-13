package dm.sample.mova.ui.screens.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Country
import dm.sample.mova.ui.components.HeaderWithButtonAndTitle
import dm.sample.mova.ui.components.MovaTwoButtons
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.createaccount.AvatarIcon
import dm.sample.mova.ui.components.createaccount.Email
import dm.sample.mova.ui.components.createaccount.FullName
import dm.sample.mova.ui.components.createaccount.Gender
import dm.sample.mova.ui.components.createaccount.Nickname
import dm.sample.mova.ui.components.createaccount.PhoneNumber
import dm.sample.mova.ui.viewmodel.createaccount.FillYourProfileViewModel

@Composable
fun FillYourProfileScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
) {
    val viewModel = hiltViewModel<FillYourProfileViewModel>()
    val state by viewModel.uiState.collectAsState()

    FillYourProfileScreenContent(
        fullName = state.fullName,
        nickname = state.nickname,
        email = state.email,
        phoneNumber = state.phoneNumber,
        onFullNameChanged = viewModel::onFullNameChanged,
        onNicknameChanged = viewModel::onNicknameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
        onGenderChanged = viewModel::onGenderChanged,
        onBackClick = onBackClick,
        countryList = viewModel.countriesList,
        selectedCountry = state.selectedCountry,
        onCountrySelected = viewModel::onSelectedCountryChanged,
        onContinueClick = onContinueClick,
        onUpdateProfileClick = {
            viewModel.onUpdateProfile().invokeOnCompletion {
                onContinueClick() // continue when complete update profile
            }
        }
    )
}

@Composable
fun FillYourProfileScreenContent(
    fullName: String,
    nickname: String,
    email: String,
    phoneNumber: String,
    countryList: List<Country>,
    selectedCountry: Country,
    onCountrySelected: (Country) -> Unit,
    onFullNameChanged: (String) -> Unit,
    onNicknameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    onUpdateProfileClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        HeaderWithButtonAndTitle(
            title = R.string.fill_your_profile_title,
            onBackClick = onBackClick
        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            MovaVerticalSpacer(height = 35.dp)
            AvatarIcon(
                onAvatarEdit = {}
            )
            FullName(
                value = fullName,
                onValueChanged = onFullNameChanged,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 12.dp)
                    .padding(horizontal = 24.dp)
            )
            Nickname(
                value = nickname,
                onValueChanged = onNicknameChanged,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
            Email(
                value = email,
                onValueChanged = onEmailChanged,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
            PhoneNumber(
                value = phoneNumber,
                onValueChanged = onPhoneNumberChanged,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                countryList = countryList,
                selectedCountry = selectedCountry,
                onCountrySelected = onCountrySelected
            )
            Gender(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                onSelectGender = onGenderChanged,
            )
            MovaVerticalSpacer(height = 50.dp)
            MovaTwoButtons(
                firstText = R.string.skip_text,
                secondText = R.string.continue_text,
                onFirstClick = onContinueClick,
                onSecondClick = onUpdateProfileClick,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            MovaVerticalSpacer(height = 24.dp)
        }
    }
}