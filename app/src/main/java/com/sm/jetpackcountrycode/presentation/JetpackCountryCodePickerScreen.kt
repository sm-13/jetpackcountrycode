package com.sm.jetpackcountrycode.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sm.jetpackcountrycode.data.appdata.FLAG_BASE_URL
import com.sm.jetpackcountrycode.data.model.Country
import com.sm.jetpackcountrycode.presentation.components.CountryPickerModalBottomSheet
import com.sm.jetpackcountrycode.ui.theme.BBlue
import com.sm.jetpackcountrycode.ui.theme.BSky
import com.sm.jetpackcountrycode.ui.theme.Beige
import com.sm.jetpackcountrycode.ui.theme.OffWhite
import com.sm.jetpackcountrycode.ui.theme.poppin_font
import com.sm.jetpackcountrycode.utils.JsonUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ConfigurationScreenWidthHeight")
@Preview
@Composable
fun JetpackCountryCodePickerScreen() {

    val context = LocalContext.current

    val countries = remember {
        JsonUtils.loadCountryListFromAssets(context = context)
    }


    var isModalBottomSheetOpen by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            false
        }
    )

    var phoneNumber by remember {
        mutableStateOf("")
    }

    var selectedCountry by remember {
        mutableStateOf<Country>(countries.first())
    }

    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()


    val animatedBorderColor by animateColorAsState(
        targetValue = if (isFocused) BBlue else BSky.copy(0.7f),
        animationSpec = tween(durationMillis = 300),
        label = "borderColor"
    )

    var warningMessage by remember { mutableStateOf<String>("") }

    LaunchedEffect(phoneNumber) {
        val phoneNumber = phoneNumber.toString().trim().filter { it.isDigit() }
        warningMessage = when {
            phoneNumber.isEmpty() -> ""
            selectedCountry.phoneLength == null -> "No phone length defined for ${selectedCountry.name}"
            !selectedCountry.phoneLength!!.contains(phoneNumber.length) -> {
                val allowedLengths = selectedCountry.phoneLength!!.joinToString(" or ")
                "Phone number must be $allowedLengths digits"
            }

            else -> ""
        }.toString()
    }



    Scaffold(
        containerColor = OffWhite,
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = innerPadding.calculateTopPadding(), start = 20.dp, end = 20.dp)
            ) {
                Spacer(Modifier.height(50.dp))
                Text(
                    warningMessage,
                    style = TextStyle(
                        color = Color.Red,
                        fontFamily = poppin_font,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp) // Border radius for background
                        )
                        .border(
                            width = 2.dp,
                            color = animatedBorderColor,
                            shape = RoundedCornerShape(8.dp)
                        ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                                .clickable(
                                    onClick = {
                                        isModalBottomSheetOpen = true
                                        coroutineScope.launch {
                                            sheetState.expand()
                                        }
                                    }
                                )
                                .border(width = 0.dp, color = Color.Transparent)
                                .padding(start = 12.dp, end = 6.dp, top = 10.dp, bottom = 10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("$FLAG_BASE_URL${selectedCountry.image}") // or any valid image URL
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "Flag Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape),
                                    onError = {
                                        Log.e("coil error", "Image failed", it.result.throwable)
                                    }
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    selectedCountry.dialCode,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Black,
                                        fontFamily = poppin_font,
                                    )
                                )
                            }
                        }
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            value = phoneNumber,
                            onValueChange = {
                                if (it.matches(Regex("^\\d*$"))) {
                                    phoneNumber = it
                                }

                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            textStyle = TextStyle(
                                fontSize = 24.sp,
                                color = Color.Black,
                                fontFamily = poppin_font,
                                fontWeight = FontWeight.Medium
                            ),
                            interactionSource = interactionSource,
                            decorationBox = { innerTextField ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(
                                                start = 6.dp,
                                                end = 6.dp,
                                                top = 10.dp,
                                                bottom = 10.dp
                                            )
                                    ) {
                                        if (phoneNumber.isEmpty()) {
                                            Text(
                                                text = "Enter phone number",
                                                style = TextStyle(
                                                    fontSize = 24.sp,
                                                    color = Color.Gray,
                                                    fontFamily = poppin_font,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }

                                        innerTextField()
                                    }
                                }
                            }
                        )
                    }

                }

                Spacer(Modifier.height(40.dp))
                AnimatedVisibility(
                    visible = phoneNumber.isNotEmpty() && warningMessage.isEmpty(),
                ) {
                    Text("Phone number: ${selectedCountry.dialCode}-$phoneNumber",
                        fontSize = 20.sp, fontWeight = FontWeight.Medium, fontFamily = poppin_font, color = BBlue)
                }


            }






            AnimatedVisibility(
                visible = isModalBottomSheetOpen,
            ) {
                ModalBottomSheet(
                    containerColor = Color.Transparent,
                    sheetState = sheetState,
                    onDismissRequest = {
                        isModalBottomSheetOpen = false
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    },
                    dragHandle = {

                    }
                ) {
                    CountryPickerModalBottomSheet(
                        countries = countries,
                        onCloseModal = {
                            isModalBottomSheetOpen = false
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                        },
                        onSelectedCountry = {
                            selectedCountry = it
                            isModalBottomSheetOpen = false
                            coroutineScope.launch {
                                sheetState.hide()
                            }
                            phoneNumber = ""
                        }
                    )
                }
            }
        }
    )
}

