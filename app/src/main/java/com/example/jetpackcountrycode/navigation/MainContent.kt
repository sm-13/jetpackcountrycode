package com.example.jetpackcountrycode.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcountrycode.view.auth.PhoneNumberUI

@Composable
fun MainContent(){
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = Screen.PhoneVerify.route){
            composable(Screen.PhoneVerify.route){
                PhoneNumberUI()
            }
        }
    }
}