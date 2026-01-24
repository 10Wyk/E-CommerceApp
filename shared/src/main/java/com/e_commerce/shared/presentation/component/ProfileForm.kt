package com.e_commerce.shared.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.e_commerce.shared.domain.model.Country
import com.e_commerce.shared.presentation.component.dialog.CountryPickerDialog
import com.e_commerce.shared.presentation.component.textfield.AlertTextField
import com.e_commerce.shared.presentation.component.textfield.CustomTextField

@Composable
fun ProfileForm(
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    firstNameError: Boolean = false,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    lastNameError: Boolean = false,
    email: String,
    country: Country,
    onCountryPick: (Country) -> Unit,
    city: String?,
    onCityChange: (String) -> Unit,
    postalCode: Int?,
    onPostalCodeChange: (String) -> Unit,
    phoneNumber: String?,
    onPhoneNumberChange: (String) -> Unit,
    address: String?,
    onAddressChange: (String) -> Unit
) {
    var dialogVisibility by remember { mutableStateOf(false) }

    if (dialogVisibility) CountryPickerDialog(
        country = country,
        onDismiss = {
            dialogVisibility = false
        },
        onConfirmClick = onCountryPick
    )

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            modifier = Modifier.fillMaxWidth(),
            isError = firstNameError,
            placeholder = "First Name"
        )

        CustomTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            modifier = Modifier.fillMaxWidth(),
            isError = lastNameError,
            placeholder = "Last Name"
        )

        CustomTextField(
            value = email,
            onValueChange = {},
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Email"
        )

        CustomTextField(
            value = city.orEmpty(),
            onValueChange = onCityChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "City"
        )

        CustomTextField(
            value = postalCode?.toString().orEmpty(),
            onValueChange = onPostalCodeChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Postal Code"
        )

        CustomTextField(
            value = address.orEmpty(),
            onValueChange = onAddressChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Address"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AlertTextField(
                text = "+${country.dialCode}",
                icon = country.flag
            ) {
                dialogVisibility = true
            }

            Spacer(modifier = Modifier.width(12.dp))

            CustomTextField(
                value = phoneNumber.orEmpty(),
                onValueChange = onPhoneNumberChange,
                modifier = Modifier.weight(1f),
                placeholder = "Phone Number"
            )
        }
    }
}