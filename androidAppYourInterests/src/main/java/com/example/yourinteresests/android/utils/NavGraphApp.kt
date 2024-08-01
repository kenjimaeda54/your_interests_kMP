package com.example.yourinteresests.android.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.exampl.yourinteresests.android.ui.ProfileScreen
import com.example.yourinteresests.android.ui.FavoriteScreen.FavoriteScreen
import com.example.yourinteresests.android.ui.SearchScreen.SearchScreen

@Composable
fun NavGraphApp(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Favorite.route
    ) {
        composable(BottomBarScreen.Favorite.route) {
            FavoriteScreen()
        }

        composable(BottomBarScreen.Search.route) {
            SearchScreen()
        }

        composable(BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }


}