package com.example.yourinteresests.android.utils

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.exampl.yourinteresests.android.ui.ProfileScreen
import com.example.yourinteresests.android.ui.SearchScreen.SearchScreen
import com.example.yourinteresests.android.ui.screens.confirmcode.ConfirmCodeScreen
import com.example.yourinteresests.android.ui.screens.detailsplace.SharedDetailsPlace
import com.example.yourinteresests.android.ui.screens.finisheduserregister.FinishedUserRegister
import com.example.yourinteresests.android.ui.screens.proflescreen.FavoriteScreen.NearbyInterests
import com.example.yourinteresests.android.ui.screens.singup.SingUpScreen
import com.example.yourinterest.viewmodel.RecoveryLocationViewModel
import com.example.yourinterest.viewmodel.SearchPlacesByQueryViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavGraphApp(navController: NavHostController, isShowBottomBar: MutableState<Boolean>) {

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = StackScreens.SingUp.name
        ) {
            composable(BottomBarScreen.NearbyInterests.route) {
                NearbyInterests(isShowBottomBar = isShowBottomBar)
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
                if (place.placesByQuery.value.data == null ) return@composable
                val findPlace = place.placesByQuery.value.data!!.find { placeByQuery -> placeByQuery.fsqId == it.arguments?.getString("fsqId") }
                if(findPlace == null) return@composable
                SharedDetailsPlace(
                    place = findPlace,
                    navController = navController
                    )
            }

            composable(StackScreens.SingUp.name) {
                SingUpScreen(navController = navController)
            }

            composable(StackScreens.ConfirmCode.name) {
                ConfirmCodeScreen()
            }

            composable(StackScreens.FinishedUserRegister.name) {
                FinishedUserRegister()
            }


            composable(BottomBarScreen.Profile.route) {
                ProfileScreen()
            }
        }
    }



}