package dm.sample.mova.ui.components.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dm.sample.mova.R
import dm.sample.mova.domain.entities.Country
import dm.sample.mova.ui.screens.createaccount.utils.NoRippleInteractionSource
import dm.sample.mova.ui.screens.createaccount.utils.getFlagEmojiFor
import dm.sample.mova.ui.theme.bodyLargeSemiBold

@Composable
fun CountryPickerView(
    hasText: Boolean,
    selectedCountry: Country,
    onSelection: (Country) -> Unit,
    countries: List<Country>,
    iconColor: Color,
    textColor: Color
) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentSize()
    ) {
        Text(
            modifier = Modifier.padding(start = 23.dp),
            text = getFlagEmojiFor(selectedCountry.nameCode)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down_light),
            contentDescription = "",
            tint = iconColor,
            modifier = Modifier
                .padding(start = 11.dp, end = 15.dp)
                .clickable { showDialog = true }
        )
        if (hasText) Text(
            modifier = Modifier.padding(end = 2.dp),
            text = "+${selectedCountry.code}",
            style = bodyLargeSemiBold.copy(lineHeight = 19.6.sp),
            color = textColor
        )
    }

    if (showDialog) CountryCodePickerDialog(countries, onSelection) {
        showDialog = false
    }
}

@Composable
fun CountryCodePickerDialog(
    countries: List<Country>,
    onSelection: (Country) -> Unit,
    dismiss: () -> Unit,
) {
    Dialog(onDismissRequest = dismiss) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 40.dp)
        ) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colors.background
                    )
            ) {
                for (country in countries) {
                    item {
                        Text(
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        onSelection(country)
                                        dismiss()
                                    },
                                    indication = null,
                                    interactionSource = remember { NoRippleInteractionSource() }
                                )
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "${getFlagEmojiFor(country.nameCode)} ${country.fullName}",
                            style = bodyLargeSemiBold.copy(lineHeight = 19.6.sp),
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}