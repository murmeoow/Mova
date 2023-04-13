package dm.sample.mova.ui.components.profile.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Account
import dm.sample.mova.domain.entities.Country
import dm.sample.mova.ui.components.MovaRedButton
import dm.sample.mova.ui.components.MovaVerticalSpacer
import dm.sample.mova.ui.components.createaccount.CountryField
import dm.sample.mova.ui.components.createaccount.Email
import dm.sample.mova.ui.components.createaccount.FullName
import dm.sample.mova.ui.components.createaccount.Gender
import dm.sample.mova.ui.components.createaccount.Nickname
import dm.sample.mova.ui.components.createaccount.PhoneNumber


@Composable
fun ProfileEditContent(
    countryList: List<Country>,
    defaultPhoneCountry: Country,
    account: Account,
    onUpdate: (Account) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .navigationBarsPadding(),
    ) {


        var fullName by remember { mutableStateOf(account.fullname ?: "") }
        var nickName by remember { mutableStateOf(account.nickname ?: "") }
        var email by remember { mutableStateOf(account.email ?: "") }
        var phoneNumber by remember { mutableStateOf(account.phone ?: "") }
        var phoneCodeCountry by remember { mutableStateOf(defaultPhoneCountry) }
        var country by remember { mutableStateOf(account.country) }
        var gender by remember { mutableStateOf(account.gender) }

        MovaVerticalSpacer(height = 10.dp)

        FullName(
            value = fullName,
            onValueChanged = { fullName = it },
        )

        MovaVerticalSpacer(height = 24.dp)

        Nickname(
            value = nickName,
            onValueChanged = { nickName = it },
        )

        MovaVerticalSpacer(height = 24.dp)

        Email(
            value = email,
            onValueChanged = { email = it },
        )

        MovaVerticalSpacer(height = 24.dp)

        PhoneNumber(
            value = phoneNumber,
            onValueChanged = { phoneNumber = it },
            countryList = countryList,
            selectedCountry = phoneCodeCountry,
            onCountrySelected = { phoneCodeCountry = it }
        )

        MovaVerticalSpacer(height = 24.dp)

        Gender(
            onSelectGender = {
                gender = it
            },
            defaultValue = gender,
        )

        MovaVerticalSpacer(height = 24.dp)

        CountryField(
            selectedCountry = country,
            onSelection = {
                country = it
            },
            countries = countryList,
        )

        MovaVerticalSpacer(height = 24.dp)

        MovaRedButton(
            buttonText = R.string.profile_update,
            onClick = {
                onUpdate(
                    account.copy(
                        fullname = fullName,
                        nickname = nickName,
                        gender = gender,
                        email = email,
                        phone = phoneNumber,
                        phoneCode = phoneCodeCountry.code,
                        country = country
                    ),
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )

        MovaVerticalSpacer(height = 70.dp)

    }
}