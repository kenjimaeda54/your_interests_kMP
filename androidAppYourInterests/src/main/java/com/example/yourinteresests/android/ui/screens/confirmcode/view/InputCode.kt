package com.example.yourinteresests.android.ui.screens.confirmcode.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourinteresests.android.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark

@Composable
fun InputCode(value: String, onValueChange: (String) -> Unit, width: Int, height: Int) {


    OutlinedTextField(
        modifier = Modifier
            .width(width = width.dp)
            .height(height = height.dp), value = value, onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = fontsKulimPark,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}


@Preview
@Composable
fun InputCodePreview() {
    YourInterestTheme {
        InputCode(value = "1", onValueChange = {}, width = 100, height = 100)
    }
}