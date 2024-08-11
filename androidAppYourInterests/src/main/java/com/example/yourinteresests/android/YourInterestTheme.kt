package com.example.yourinteresests.android

import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.yourinteresests.android.theme.Typography
import com.example.yourinteresests.android.theme.black
import com.example.yourinteresests.android.theme.blue
import com.example.yourinteresests.android.theme.gray
import com.example.yourinteresests.android.theme.green
import com.example.yourinteresests.android.theme.white
import com.example.yourinteresests.android.theme.error

private val lightColorScheme = lightColorScheme(
   primary = white,
    secondary =  blue,
    tertiary =  green,
    primaryContainer = black,
    onPrimaryContainer = gray,
    error = error
    )

@Composable
fun YourInterestTheme(
     content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = lightColorScheme,
        typography = Typography,
         content = content
    )
}
