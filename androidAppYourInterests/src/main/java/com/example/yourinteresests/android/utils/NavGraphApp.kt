package com.example.yourinteresests.android.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.exampl.yourinteresests.android.ui.ProfileScreen
import com.example.yourinteresests.android.ui.SearchScreen.SearchScreen
import com.example.yourinteresests.android.ui.screens.detailsplace.DetailsPlace
import com.example.yourinteresests.android.ui.screens.proflescreen.FavoriteScreen.NearbyInterests
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel
import com.example.yourinterest.viewmodel.RecoveryLocationViewModel
import com.example.yourinterest.viewmodel.SearchPlacesByQueryViewModel


@SuppressLint("UnrememberedGetBackStackEntry")
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

            val location = viewModel<RecoveryLocationViewModel>(parent)
            if( location.location.value.data == null) return@composable
            SearchScreen( location = location.location.value.data!!,navController = navController)
        }

        composable(StackScreens.DetailsPlace.name + "/{fsqId}", arguments = listOf(navArgument("fsqId") { type = NavType.StringType })) {
            val parentSearchRoute = remember(it) {
                navController.getBackStackEntry(BottomBarScreen.Search.route)
            }
            val parentNearbyRoute = remember {
                navController.getBackStackEntry(BottomBarScreen.NearbyInterests.route)
            }
            val place =  viewModel<SearchPlacesByQueryViewModel>(parentSearchRoute)
            val location = viewModel<RecoveryLocationViewModel>(parentNearbyRoute)
            if (place.placesByQuery.value.data == null || location.location.value.data == null) return@composable
            val findPlace = place.placesByQuery.value.data!!.find { placeByQuery -> placeByQuery.fsqId == it.arguments?.getString("fsqId") }
            if(findPlace == null) return@composable
            DetailsPlace(place = findPlace, location = location.location.value.data!!)
        }

        composable(BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
    }


}