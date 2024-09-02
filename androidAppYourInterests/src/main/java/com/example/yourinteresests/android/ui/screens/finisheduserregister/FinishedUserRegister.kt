package com.example.yourinteresests.android.ui.screens.finisheduserregister

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.finisheduserregister.view.CustomOutlineTextField
import com.mapbox.maps.extension.style.expressions.dsl.generated.mod
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FinishedUserRegister(phone: String) {
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = FocusRequester()
    val cameraState = rememberCameraState()
    val camSelector by rememberCamSelector(CamSelector.Back)
    val userName by remember {
        mutableStateOf("")
    }
    val modalSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }, skipHalfExpanded = false
    )



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.7f)),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                painter = painterResource(id = com.example.yourinteresests.android.R.drawable.photo_user),
                contentDescription = "Image profile"
            )
            Text(
                text = "Clique acima para alterar a foto",
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Light,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primaryContainer
            )
            Column(
                modifier = Modifier.padding(horizontal = configuration.screenWidthDp.dp * 0.1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 10.dp, top = 40.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = "Nome:",
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    Text(
                        modifier = Modifier.clickable {
                            coroutineScope.launch {
                                modalSheetState.show()
                                focusRequester.requestFocus()
                            }
                        },
                        text = "Clique para informar o nome",
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    Text(
                        text = "Telefone:",
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                    Text(
                        text = "phone",
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )

                }
            }
        }


    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {
            Column(
                modifier = Modifier.padding(top = 10.dp, bottom = 15.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomOutlineTextField(
                    modifier = Modifier.focusRequester(focusRequester = focusRequester),
                    placeHolder = "ex: João da Silva",
                    value = userName,
                    onValueChange = {

                    })
            }

        }) {

    }
}


@Composable
@Preview
fun FinishedUserRegisterPreview() {
    FinishedUserRegister(phone = "+55355555555")
}