package com.example.yourinteresests.android.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightColorScheme = lightColorScheme(
    primary = white,
    secondary = blue,
    tertiary = green,
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
