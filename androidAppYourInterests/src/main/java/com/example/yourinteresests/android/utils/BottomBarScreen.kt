package com.example.yourinteresests.android.utils

sealed  class BottomBarScreen (
    val route: String,
    val icon: Int

) {

    data object NearbyInterests : BottomBarScreen(
        route = "nearbyInterests",
        icon = com.example.yourinteresests.android.R.drawable.pin
    )

    data object Search : BottomBarScreen(
        route = "search",
        icon = com.example.yourinteresests.android.R.drawable.search
    )

    data object Profile : BottomBarScreen(
        route = "profile",
        icon = com.example.yourinteresests.android.R.drawable.profile
    )



}