package com.example.yourinteresests.android.utils

class BottomScreens {
    companion object{
        fun screens(): List<BottomBarScreen> {
            return listOf(
                BottomBarScreen.NearbyInterests,
                BottomBarScreen.Search,
                BottomBarScreen.Profile,
                )
        }
    }


}