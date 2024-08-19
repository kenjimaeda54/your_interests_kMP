package com.example.yourinteresests.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.exampl.yourinteresests.android.ui.ProfileScreen
import com.example.yourinteresests.android.ui.SearchScreen.SearchScreen
import com.example.yourinteresests.android.ui.screens.proflescreen.FavoriteScreen.NearbyInterests
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel



@Composable
fun NavGraphApp(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.NearbyInterests.route
    ) {
        composable(BottomBarScreen.NearbyInterests.route) {
            NearbyInterests()
        }

        composable(BottomBarScreen.Search.route) {
            val parent = remember(it) {
                navController.getBackStackEntry(BottomBarScreen.NearbyInterests.route)
            }
            val photosPlaces = viewModel<PlacesNearbyViewModel>(parent)
            if(photosPlaces.placesNearby.value.data == null) return@composable
            SearchScreen(photosPlaces = photosPlaces.placesNearby.value.data!!)
        }

        composable(BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }


}