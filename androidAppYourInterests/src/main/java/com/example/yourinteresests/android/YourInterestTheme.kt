package com.example.yourinteresests.android

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
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
