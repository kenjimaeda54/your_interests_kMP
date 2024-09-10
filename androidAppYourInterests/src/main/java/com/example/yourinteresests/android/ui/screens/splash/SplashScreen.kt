package com.example.yourinteresests.android.ui.screens.splash

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.yourinteresests.R
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieRetrySignal
import com.example.yourinteresests.android.ui.screens.MainScreen
import com.example.yourinteresests.android.utils.BottomBarScreen
import com.example.yourinteresests.android.utils.ComposableLifecycle
import com.example.yourinteresests.android.utils.StackScreens
import com.example.yourinterest.viewmodel.UserSapabaseViewModel

//tentar descobrir quando acaba
//https://airbnb.io/lottie/#/android-compose
@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.example.yourinteresests.android.R.raw.splash_animation))
    val logoAnimationState =
        animateLottieCompositionAsState(composition = composition)
    val progress by animateLottieCompositionAsState(composition = composition)
    val userViewModel = viewModel<UserSapabaseViewModel>()
    val user by userViewModel.user.collectAsState()

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            userViewModel.fetchUser()
        }

    }

    if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
        if (user.data == null) {
            navController.navigate(StackScreens.SingUp.name) {
                popUpTo(StackScreens.SplashScreen.name) {
                    inclusive = true
                }
            }
        }else {
            navController.navigate(BottomBarScreen.NearbyInterests.route) {
                popUpTo(StackScreens.SplashScreen.name) {
                    inclusive = true
                }
            }
        }
    }else {
        LottieAnimation(composition = composition, progress = { progress })
    }

}