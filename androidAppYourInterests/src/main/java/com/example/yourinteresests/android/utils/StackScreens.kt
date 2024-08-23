package com.example.yourinteresests.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import okhttp3.Route

enum class StackScreens {
    DetailsPlace;

     companion object {
         fun fromRoute(route: String): StackScreens = when(route.substringBefore("/")) {
             DetailsPlace.name -> DetailsPlace
             else -> throw  IllegalArgumentException("Route $route is not recognized.")
         }
     }
} 