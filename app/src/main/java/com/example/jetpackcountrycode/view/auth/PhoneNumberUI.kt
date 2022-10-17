package com.example.jetpackcountrycode.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcountrycode.ui.theme.darkblue
import com.example.jetpackcountrycode.ui.theme.poppins

@Composable
fun PhoneNumberUI(){
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(darkblue)
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(72.dp))
            Text(
                text = "Enter your\nCell Phone\nNumber",
                style = TextStyle(
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 48.sp
                ),
                lineHeight = 58.sp
            )
//            WANT TO ADD TOGISOFT JETPACK COUNTRY CODE PICKER HERE

        }
    }
}

@Composable
@Preview
fun PhoneNumberUIPreview(){
    PhoneNumberUI()
}