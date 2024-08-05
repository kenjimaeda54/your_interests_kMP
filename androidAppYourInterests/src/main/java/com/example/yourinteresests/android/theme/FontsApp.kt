package com.example.yourinteresests.android.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.yourinteresests.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val fontsKulimPark = FontFamily(
    Font(com.example.yourinteresests.android.R.font.kulimpark_bold),
    Font(com.example.yourinteresests.android.R.font.kulimpark_regular),
    Font(com.example.yourinteresests.android.R.font.kulimpark_light),
    Font(com.example.yourinteresests.android.R.font.kulimpark_semibold)
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