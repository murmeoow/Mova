package dm.sample.mova.ui.components.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import dm.sample.mova.R
import dm.sample.mova.domain.Constants
import dm.sample.mova.domain.entities.Country
import dm.sample.mova.ui.components.SampleTextField
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.theme.MovaTheme
import dm.sample.mova.ui.theme.Grayscale500
import dm.sample.mova.ui.theme.Grayscale900
import dm.sample.mova.ui.theme.Primary500
import dm.sample.mova.ui.theme.TransparentRed
import dm.sample.mova.ui.theme.White
import dm.sample.mova.ui.theme.bodyLargeSemiBold
import dm.sample.mova.ui.theme.bodyMediumRegular
import dm.sample.mova.ui.utils.isDarkTheme
import com.airbnb.android.showkase.annotation.ShowkaseComposable

@Composable
fun FullName(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SampleTextField(
        value = value,
        onValueChange = onValueChanged,
        placeholderId = R.string.fill_your_profile_full_name,
        modifier = modifier
    )
}

@Composable
fun Nickname(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SampleTextField(
        value = value,
        onValueChange = onValueChanged,
        placeholderId = R.string.fill_your_profile_nickname,
        modifier = modifier
    )
}

@Composable
fun Email(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SampleTextField(
        value = value,
        onValueChange = onValueChanged,
        placeholderId = R.string.fill_your_profile_email,
        trailingIcon = R.drawable.ic_message,
        modifier = modifier
    )
}

@Composable
fun PhoneNumber(
    value: String,
    onValueChanged: (String) -> Unit,
    countryList: List<Country>,
    selectedCountry: Country,
    onCountrySelected: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    var hasFocus by rememberSaveable {
        mutableStateOf(false)
    }
    val textColor: Color
    val iconsColor: Color
    val backgroundColor = if (hasFocus) TransparentRed else MaterialTheme.colors.surface

    if (isDarkTheme()) {
        textColor = if (value.isEmpty()) Grayscale500 else White
        iconsColor = if (hasFocus || value.isNotEmpty()) White else Grayscale500
    } else {
        textColor = if (value.isEmpty()) Grayscale500 else Grayscale900
        iconsColor = if (hasFocus || value.isNotEmpty()) Grayscale900 else Grayscale500
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = bodyLargeSemiBold.copy(lineHeight = 19.6.sp),
        leadingIcon = {
            CountryPickerView(
                countries = countryList,
                selectedCountry = selectedCountry,
                onSelection = { onCountrySelected(it) },
                iconColor = iconsColor,
                textColor = textColor,
                hasText = value.isNotBlank()
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.fill_your_profile_phone_number),
                style = bodyMediumRegular
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = textColor,
            leadingIconColor = Primary500,
            unfocusedLabelColor = textColor,
            focusedLabelColor = Primary500,
            unfocusedBorderColor = MaterialTheme.colors.surface,
            focusedBorderColor = Primary500,
            backgroundColor = backgroundColor,
            placeholderColor = Grayscale500
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        ),
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth()
            .onFocusChanged { hasFocus = it.hasFocus }
    )
}

@Composable
fun Gender(
    modifier: Modifier = Modifier,
    defaultValue: String? = null,
    onSelectGender: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf(
        stringResource(R.string.gender_male),
        stringResource(R.string.gender_female), stringResource(R.string.gender_non_binary),
    )
    var selectedText by remember { mutableStateOf(defaultValue ?: "") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Column(modifier = modifier) {
        SampleTextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            placeholderId = R.string.fill_your_profile_gender,
            trailingIcon = R.drawable.ic_arrow_down,
            trailingIconClick = { expanded = expanded.not() },
            modifier = Modifier.onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
            onClick = { expanded = true }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .background(color = MaterialTheme.colors.surface)
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = label
                        onSelectGender(label)
                        expanded = false
                    },
                    interactionSource = remember { NoRippleInteractionSource() },
                ) {
                    Text(
                        text = label,
                        style = bodyLargeSemiBold.copy(lineHeight = 19.6.sp),
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun CountryField(
    selectedCountry: Country?,
    onSelection: (Country) -> Unit,
    countries: List<Country>,
) {
    var showDialog by remember { mutableStateOf(false) }

    SampleTextField(
        value = selectedCountry?.fullName ?: "",
        onValueChange = { },
        readOnly = true,
        placeholderId = R.string.fill_your_profile_country,
        trailingIcon = R.drawable.ic_arrow_down,
        trailingIconClick = {
            showDialog = true
        },
        onClick = { showDialog = true }
    )

    if (showDialog) CountryCodePickerDialog(countries, onSelection) {
        showDialog = false
    }
}

@ShowkaseComposable(
    name = "Country Field",
    group = Constants.SHOWKASE_GROUP_COMPONENTS,
)
@Preview(showBackground = true)
@Composable
fun PreviewCountryField() {
    MovaTheme(darkTheme = isSystemInDarkTheme()) {
        val countries = listOf(
            Country(
                "tm",
                "+993",
                "Turkmenistan"
            ),
            Country(
                "ru",
                "+7",
                "Russia"
            ),
            Country(
                "US",
                "+1",
                "United States"
            ),
        )
        CountryField(
            selectedCountry = countries.first(),
            onSelection = {

            },
            countries = countries,
        )
    }
}