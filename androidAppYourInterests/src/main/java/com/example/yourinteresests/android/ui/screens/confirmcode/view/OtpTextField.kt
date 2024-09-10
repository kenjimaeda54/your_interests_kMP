package com.example.yourinteresests.android.ui.screens.confirmcode.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourinteresests.android.theme.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
    sizeInput: Dp,
    onSubmit: () -> Unit
) {
    val localFocus = LocalFocusManager.current


    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = KeyboardActions(onDone = {
           onSubmit()
           localFocus.clearFocus()
        }),
        singleLine = true,
        maxLines = 1,
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText,
                        sizeInput = sizeInput
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    sizeInput: Dp
) {
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Box(
        modifier = Modifier
            .width(sizeInput)
            .height(sizeInput)
            .border(
                1.dp,
                MaterialTheme.colorScheme.primaryContainer.copy(0.7f),
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            maxLines = 1,
            text = char,
            style = TextStyle(
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primaryContainer
            ),
            textAlign = TextAlign.Center
        )
    }

}

@Composable
@Preview
fun OtpTextFieldPreview() {
    YourInterestTheme {
        OtpTextField(otpText = "123456", onOtpTextChange = { _, _ -> }, sizeInput = 40.dp,onSubmit = {  })
    }
}