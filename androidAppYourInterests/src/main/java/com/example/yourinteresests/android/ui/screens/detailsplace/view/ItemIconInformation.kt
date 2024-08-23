package com.example.yourinteresests.android.ui.screens.detailsplace.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourinteresests.android.theme.fontsKulimPark

@Composable
fun ItemIconInformation(subtitle: String, information: String, contentIcon: @Composable () -> Unit) {

    Row(horizontalArrangement = Arrangement.spacedBy(13.dp), verticalAlignment = Alignment.CenterVertically) {
        contentIcon()
        Column {
            Text(
                information,
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(text = subtitle,
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primaryContainer

            )
        }

    }


}