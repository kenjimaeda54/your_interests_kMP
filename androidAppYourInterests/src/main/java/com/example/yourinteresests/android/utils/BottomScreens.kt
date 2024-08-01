package com.example.yourinteresests.android.utils

class BottomScreens {
    companion object{
        fun screens(): List<BottomBarScreen> {
            return listOf(
                BottomBarScreen.Favorite,
                BottomBarScreen.Search,
                BottomBarScreen.Profile,
                )
        }
    }


}