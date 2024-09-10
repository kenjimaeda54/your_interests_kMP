package com.example.yourinteresests.android.utils

enum class StackScreens {
    SplashScreen,
    DetailsPlace,
    SingUp,
    ConfirmCode,
    FinishedUserRegister,
    ShowMap;

    companion object {
        fun fromRoute(route: String): StackScreens = when (route.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            DetailsPlace.name -> DetailsPlace
            ShowMap.name -> ShowMap
            SingUp.name -> SingUp
            ConfirmCode.name -> ConfirmCode
            FinishedUserRegister.name -> FinishedUserRegister
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
    }
} 