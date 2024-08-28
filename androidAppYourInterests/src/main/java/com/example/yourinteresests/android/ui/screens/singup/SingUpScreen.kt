package com.example.yourinteresests.android.ui.screens.singup

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinterest.viewmodel.AuthSapabaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingUpScreen() {
    var phoneUser by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val configuration = LocalConfiguration.current
    val interactionSource = remember { MutableInteractionSource() }
    val buttonInteractionSourceClickable by interactionSource.collectIsPressedAsState()
    val viewModel = viewModel<AuthSapabaseViewModel>()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 13.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bem vindo",
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(bottom = 3.dp))
            Text(
                text = "Seus interesses na palma da mao",
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Normal, fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(bottom = configuration.screenHeightDp.dp * 0.09f))
            Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary.copy(0.95f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 15.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "Numero de telefone",
                    fontFamily = fontsKulimPark,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                Spacer(modifier = Modifier.padding(bottom = 15.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 25.dp,
                                spotColor = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(3.dp),

                                )
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .size(width = 25.dp, height = 25.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+55", fontFamily = fontsKulimPark,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )

                    }
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 25.dp,
                                spotColor = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(3.dp),

                                )
                            .background(MaterialTheme.colorScheme.primary.copy(0.7f))
                            .fillMaxWidth()
                    ) {
                        BasicTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = phoneUser,
                            maxLines = 1,
                            textStyle = TextStyle(
                                fontFamily = fontsKulimPark,
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ),
                            onValueChange = {
                                if (it.text.length <= 11) {
                                    phoneUser = it
                                }

                            },
                            singleLine = true,
                        ) {
                            OutlinedTextFieldDefaults.DecorationBox(
                                value = phoneUser.text,
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                                innerTextField = it,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                interactionSource = interactionSource,
                                placeholder = {
                                    Text(
                                        text = "ex: 11999999999",
                                        fontFamily = fontsKulimPark,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
                                    )
                                },
                                container = {
                                    OutlinedTextFieldDefaults.ContainerBox(
                                        enabled = true,
                                        isError = false,
                                        interactionSource = interactionSource,
                                        colors = OutlinedTextFieldDefaults.colors(

                                            disabledBorderColor = MaterialTheme.colorScheme.primary.copy(
                                                0.5f
                                            ),
                                            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(
                                                0.5f
                                            ),
                                            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(
                                                0.5f
                                            ),

                                            ),
                                        focusedBorderThickness = 0.dp,
                                        unfocusedBorderThickness = 0.dp
                                    )
                                }
                            )
                        }
                    }

                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                    contentPadding = PaddingValues(horizontal = 3.dp, vertical = 10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.secondary,
                        disabledContentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = { /*TODO*/ }) {
                    Text(
                        text = "Entrar",
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}