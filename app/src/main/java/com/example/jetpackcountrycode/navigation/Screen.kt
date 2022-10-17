package com.example.jetpackcountrycode.navigation

sealed class Screen(val route: String){
    object PhoneVerify: Screen("phoneverify")
}
