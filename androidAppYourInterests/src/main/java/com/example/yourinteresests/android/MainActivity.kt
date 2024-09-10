package com.example.yourinteresests.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.yourinteresests.android.theme.YourInterestTheme
import com.example.yourinteresests.android.ui.screens.MainScreen
import com.example.yourinteresests.android.ui.screens.finisheduserregister.FinishedUserRegister
import com.example.yourinteresests.android.ui.screens.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YourInterestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    MainScreen()
                   // FinishedUserRegister(phone = "343", navController = rememberNavController() )
                }
            }
        }
    }
}

