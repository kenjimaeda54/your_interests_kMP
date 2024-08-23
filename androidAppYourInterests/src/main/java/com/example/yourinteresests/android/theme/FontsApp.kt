package com.example.yourinteresests.android.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.yourinteresests.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val fontsKulimPark = FontFamily(
    Font(com.example.yourinteresests.android.R.font.kulimpark_bold, FontWeight.Bold),
    Font(com.example.yourinteresests.android.R.font.kulimpark_regular, FontWeight.Normal),
    Font(com.example.yourinteresests.android.R.font.kulimpark_light, FontWeight.Light),
    Font(com.example.yourinteresests.android.R.font.kulimpark_semibold, FontWeight.SemiBold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

)