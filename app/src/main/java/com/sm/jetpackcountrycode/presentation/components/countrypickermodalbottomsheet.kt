package com.sm.jetpackcountrycode.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.sm.jetpackcountrycode.data.appdata.FLAG_BASE_URL
import com.sm.jetpackcountrycode.data.model.Country
import com.sm.jetpackcountrycode.ui.theme.BSky
import com.sm.jetpackcountrycode.ui.theme.Beige
import com.sm.jetpackcountrycode.ui.theme.poppin_font
import com.sm.jetpackcountrycode.utils.SearchUtil.filterCountries

@Composable
fun CountryPickerModalBottomSheet(
    countries: List<Country>,
    onSelectedCountry: (Country) -> Unit,
    onCloseModal: () -> Unit
) {

    val searchText = rememberTextFieldState(initialText = "")
    var filteredCountries by remember { mutableStateOf(countries) }




    LaunchedEffect(searchText.text) {
        Log.d("IS CHANGED", "${searchText.text}")
        filteredCountries = filterCountries(countries, searchText.text.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xff4335A7))
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(60.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            IconButton(
//                onClick = onCloseModal
//            ) {
//                Icon(
//                    Icons.Default.Close, contentDescription = "",
//                    Modifier.size(24.dp),
//                )
//            }
            ElevatedButton(
                onClick = onCloseModal,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Beige),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .size(48.dp) // Diameter of the circle
            ) {
                Icon(
                    imageVector = Icons.Default.Close, // Or any other icon
                    contentDescription = "Close",
                    modifier = Modifier.size(32.dp),
                    tint = Color(0xff4335A7)
                )
            }
        }
        Spacer(Modifier.height(14.dp))

        CountrySearchTextField(
            searchText = searchText
        )

        Spacer(Modifier.height(20.dp))




        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            flingBehavior = ScrollableDefaults.flingBehavior()
        ) {
            items(
                filteredCountries.size
            ) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onSelectedCountry(filteredCountries[index])
                            }
                        ),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("$FLAG_BASE_URL${filteredCountries[index].image}") // or any valid image URL
                            .crossfade(true)
                            .build(),
                        contentDescription = "Flag Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        onError = {
                            Log.e("coil error", "Image failed", it.result.throwable)
                        }
                    )
                    Spacer(Modifier.width(18.dp))
                    Column {
                        Text(
                            filteredCountries[index].name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Beige,
                            fontFamily = poppin_font,
                        )
                        Text(
                            filteredCountries[index].dialCode,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Beige.copy(0.7f),
                            fontFamily = poppin_font
                        )
                    }
                }
                if(index != filteredCountries.lastIndex){
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        color = Color.White.copy(0.2f)
                    )
                }
            }
        }
    }
}












//        Column(
//            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
//        ) {
//            countries.forEach { country ->
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable(
//                            onClick = {
//                                onSelectedCountry(country)
//                            }
//                        ),
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data("$FLAG_BASE_URL${country.image}") // or any valid image URL
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = "Random Image",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .size(60.dp)
//                            .clip(CircleShape),
//                        onError = {
//                            Log.e("coil error", "Image failed", it.result.throwable)
//                        }
//                    )
//                    Column {
//                        Text(
//                            country.name,
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                        Text(
//                            country.dialCode,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Medium,
//                            color = Color.Black.copy(0.7f)
//                        )
//                    }
//                }
//                HorizontalDivider(
//                    modifier = Modifier.padding(horizontal = 10.dp)
//                )
//            }
//        }