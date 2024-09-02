package com.example.yourinteresests.android.ui.screens.finisheduserregister.view

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourinteresests.android.theme.fontsKulimPark

@OptIn(
     ExperimentalMaterial3Api::class,
)
@Composable
fun CustomOutlineTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    value: String,
    onValueChange: (text: String) -> Unit,
    actionKeyboard: (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon:  @Composable() (() -> Unit)? = null,
    singleLine: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true
    val keyboardController = LocalSoftwareKeyboardController.current

    //abaixo como contornar altura dinamica no outlineTextField e tambem a largura do width
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        enabled = enabled,
        textStyle = TextStyle(
            fontFamily = fontsKulimPark,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.primary
        ),
        keyboardActions = KeyboardActions(onDone = {
            actionKeyboard?.invoke()
            keyboardController?.hide()
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
        singleLine = false,
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(0.95f)
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = it,
            enabled = enabled,
            singleLine = singleLine,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            placeholder = {
                Text(
                    text = placeHolder, style = TextStyle(
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),

                        )
                )
            },
            leadingIcon = leadingIcon,
            colors = OutlinedTextFieldDefaults.colors(),
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 4.dp, bottom = 4.dp, end = 8.dp, start = 8.dp
            ),
        ) {
            OutlinedTextFieldDefaults.ContainerBox(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                ),
                focusedBorderThickness = 1.dp,
                unfocusedBorderThickness = 1.dp,
            )

        }
    }

}
