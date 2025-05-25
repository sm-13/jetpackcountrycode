package com.sm.jetpackcountrycode.presentation.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sm.jetpackcountrycode.data.model.Country

@Composable
fun PhoneNumberTextField(
    selectedCountry: Country,
){
    var phoneNumber by remember {
        mutableStateOf("")
    }

    val isValid = remember(phoneNumber, selectedCountry) {
        isPhoneNumberValid(phoneNumber, selectedCountry)
    }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
        value = phoneNumber,
        onValueChange = {
            phoneNumber = it
        },
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        isError = phoneNumber.isNotEmpty() && !isValid,
        supportingText = {
            if (phoneNumber.isNotEmpty() && !isValid) {
                val validLengths = selectedCountry.phoneLength?.joinToString(", ") ?: "unknown"
                Text(
                    text = "Phone number must be $validLengths digits",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
    )
}


fun isPhoneNumberValid(phoneNumber: String, country: Country?): Boolean {
    // If no country is selected or phoneLength is null, consider input invalid or skip validation
    if (country == null || country.phoneLength == null) {
        return false // Or true if you want to allow any length when phoneLength is null
    }

    // Clean the phone number: remove non-digit characters (e.g., spaces, hyphens)
    val cleanedPhoneNumber = phoneNumber.replace(Regex("[^0-9]"), "")

    // Check if the cleaned phone number length matches any of the valid lengths
    return cleanedPhoneNumber.length in country.phoneLength
}