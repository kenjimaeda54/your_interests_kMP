package com.example.yourinteresests.android.utils

enum class StackScreens {
    DetailsPlace,
    ShowMap;

    companion object {
        fun fromRoute(route: String): StackScreens = when (route.substringBefore("/")) {
            DetailsPlace.name -> DetailsPlace
            ShowMap.name -> ShowMap
            else -> throw IllegalArgumentException("Route $route is not recognized.")
        }
    }
} 