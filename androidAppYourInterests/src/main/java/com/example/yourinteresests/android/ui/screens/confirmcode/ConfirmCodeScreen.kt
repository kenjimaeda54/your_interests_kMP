package com.example.yourinteresests.android.ui.screens.confirmcode

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.yourinteresests.android.R
import com.example.yourinteresests.android.theme.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.confirmcode.view.OtpTextField
import com.example.yourinteresests.android.utils.StackScreens
import com.example.yourinterest.viewmodel.AuthSapabaseViewModel
import kotlinx.coroutines.delay
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@Composable
fun ConfirmCodeScreen(phone: String, navController: NavHostController) {
    var optTextField by remember {
        mutableStateOf("")
    }
    val viewModel = viewModel<AuthSapabaseViewModel>()
    val verifyCode by viewModel.successVerifyCodeOTP.collectAsState()
    var timer by remember { mutableIntStateOf(60) }

    LaunchedEffect(key1 = timer) {
        while (timer > 0) {
            delay(1000)
            timer -= 1
        }
    }

    LaunchedEffect(key1 = verifyCode.data) {
        if (verifyCode.data == true) {
            navController.navigate(StackScreens.FinishedUserRegister.name + "/${phone}") {
                popUpTo(StackScreens.ConfirmCode.name) {
                    inclusive = true

                }
            }
        }
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 13.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "Insira o codigo enviado pelo SMS",
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 40.dp)
                    .fillMaxWidth(0.7f),
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                color = MaterialTheme.colorScheme.primaryContainer,

                )
            OtpTextField(otpText = optTextField, onOtpTextChange ={ value,_ ->
                optTextField = value
            } , sizeInput = 60.dp) {
               viewModel.verifyCodeOTP(phone, optTextField)
            }
            if (timer > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$timer",
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontSize = 18.sp
                    )
                }
            }

            if (verifyCode.exception != null) {
                viewModel.clearData()
                MotionToast.createColorToast(
                    LocalContext.current as Activity,
                    "Falhou ☹️",
                    "Numero digitado esta incorreto",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(LocalContext.current, R.font.kulimpark_regular)

                )
            }

            if (timer == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            timer = 60
                            viewModel.sendCodeOTP(phone)
                        },
                        text = "Reenviar codigo",
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ConfirmCodeScreenPreview() {
    YourInterestTheme {
        ConfirmCodeScreen("", rememberNavController())
    }
}