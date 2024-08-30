package com.example.yourinteresests.android.ui.screens.confirmcode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourinteresests.android.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.confirmcode.view.InputCode

@Composable
fun ConfirmCodeScreen() {
    var oneTextField by remember {
        mutableStateOf("")
    }
    var twoTextField by remember {
        mutableStateOf("")
    }
    var threeTextField by remember {
        mutableStateOf("")
    }
    var fourTextField by remember {
        mutableStateOf("")
    }
    var fiveTextField by remember {
        mutableStateOf("")
    }
    var sixTextField by remember {
        mutableStateOf("")
    }
    val configuration = LocalConfiguration.current
    val focusManager = LocalFocusManager.current
    val sizeInput = (configuration.screenWidthDp.toFloat() * 0.14).toInt()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 13.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Insira o codigo enviado pelo SMS",
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 30.dp)
                    .fillMaxWidth(0.7f),
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                color = MaterialTheme.colorScheme.primaryContainer
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                InputCode(value = oneTextField, onValueChange = {
                    oneTextField = it
                    focusManager.moveFocus(FocusDirection.Next)
                }, width = sizeInput, height = sizeInput)
                InputCode(value = twoTextField, onValueChange = {
                    twoTextField = it
                    focusManager.moveFocus(FocusDirection.Next)
                }, width = sizeInput, height = sizeInput)
                InputCode(value = threeTextField, onValueChange = {
                    threeTextField = it
                    focusManager.moveFocus(FocusDirection.Next)
                }, width = sizeInput, height = sizeInput)
                InputCode(value = fourTextField, onValueChange = {
                    fourTextField = it
                    focusManager.moveFocus(FocusDirection.Next)
                }, width = sizeInput, height = sizeInput)
                InputCode(value = fiveTextField, onValueChange = {
                    fiveTextField = it
                    focusManager.moveFocus(FocusDirection.Next)
                }, width = sizeInput, height = sizeInput)
                InputCode(value = sixTextField, onValueChange = {
                    sixTextField = it
                    focusManager.clearFocus()
                }, width = sizeInput, height = sizeInput)
            }

        }
    }
}

@Preview
@Composable
fun ConfirmCodeScreenPreview() {
    YourInterestTheme {
        ConfirmCodeScreen()
    }
}