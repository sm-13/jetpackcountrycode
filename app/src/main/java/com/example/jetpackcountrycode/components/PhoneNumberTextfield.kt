package com.example.jetpackcountrycode.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.togitech.ccp.component.TogiCountryCodePicker
import com.togitech.ccp.data.utils.getDefaultLangCode
import com.togitech.ccp.data.utils.getDefaultPhoneCode
import com.togitech.ccp.data.utils.getLibCountries

@Composable
fun PhoneNumberTextfield(){
    val context = LocalContext.current
    var defaultLang by rememberSaveable {
        mutableStateOf(getDefaultPhoneCode(context))
    }
    val verifyText by remember { mutableStateOf("") }
    var phoneNumber = rememberSaveable {
        mutableStateOf("")
    }
    var phoneCode by rememberSaveable {
        mutableStateOf(getDefaultLangCode(context))
    }
    var isValidPhone by remember { mutableStateOf(true) }


    Column() {
        TogiCountryCodePicker(
            text = phoneNumber.value,
            onValueChange = {phoneNumber.value = it},
            defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
            pickedCountry = {
                            phoneCode = it.countryPhoneCode
                defaultLang = it.countryCode
            },
            error = isValidPhone
        )
    }
}