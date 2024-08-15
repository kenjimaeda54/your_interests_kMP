package com.example.yourinteresests.android.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yourinteresests.android.utils.BottomCustomNavigation
import com.example.yourinteresests.android.utils.BottomScreens
import com.example.yourinteresests.android.utils.NavGraphApp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute =
        navBackStackEntry?.destination?.route?.split("/")
    val stringBottomRoute = BottomScreens.screens().map { it.route }


    Scaffold(
        bottomBar = {
            if(stringBottomRoute.contains(currentRoute?.get(0))) currentDestination?.let {
                BottomCustomNavigation(
                    navHostController = navController,
                    navDestination = it
                )
            }
        }
    ) {
       NavGraphApp(navController = navController)
     }



}