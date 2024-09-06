package com.example.yourinteresests.android.ui.screens.finisheduserregister

import android.media.Image
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageResult
import com.example.yourinteresests.R
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.finisheduserregister.view.CustomOutlineTextField
import com.example.yourinterest.data.model.user.UserWithPhotoByTeArray
import com.example.yourinterest.viewmodel.UserSapabaseViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.CaptureMode
import com.ujizin.camposer.state.ImageCaptureResult
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun FinishedUserRegister(phone: String) {
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    val userViewModel = viewModel<UserSapabaseViewModel>()
    val focusRequester = FocusRequester()
    var isClickedShowCamera by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val cameraState = rememberCameraState()
    var camSelector by rememberCamSelector(CamSelector.Front)
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    val userName by remember {
        mutableStateOf("")
    }
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }, skipHalfExpanded = false
    )



    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.7f)),
    ) {
        if (isClickedShowCamera && cameraPermission.status.isGranted) {
            CameraPreview(
                cameraState = cameraState,
                camSelector = camSelector,
                captureMode = CaptureMode.Image
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 15.dp, end = 15.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    isClickedShowCamera = false
                                },
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Image(
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {
                                    //quando da erro de combine object é porque enum commo o CamSelector
                                    camSelector =
                                        if (camSelector == CamSelector.Back) CamSelector.Front else CamSelector.Back
                                },
                            painter = painterResource(id = com.example.yourinteresests.android.R.drawable.refresh),
                            contentDescription = "Refresh camera",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                    shape = CircleShape
                                )
                                .size(50.dp)
                                .clickable {
                                    val file =
                                        File(
                                            context.filesDir,
                                            "$phone.jpg"
                                        ).apply { createNewFile() }
                                    cameraState.takePicture(file = file) { result ->
                                        when (result) {
                                            is ImageCaptureResult.Success -> {
                                                val encoded =
                                                    Files.readAllBytes(Paths.get(result.savedUri!!.path))
                                                val userModel = UserWithPhotoByTeArray(
                                                    name = userName,
                                                    phone = phone,
                                                    photo = encoded
                                                )
                                                userViewModel.insertUser(userModel)
                                            }

                                            is ImageCaptureResult.Error -> {
                                                Log.d("Error", "Error when take picture")
                                            }
                                        }

                                    }
                                },

                            )
                    }
                }
            }

        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable {
                            isClickedShowCamera = true
                        },
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
                            text = userName.ifEmpty { "Clique para inserir seu  nome" },
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
                            text = phone,
                            fontFamily = fontsKulimPark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )

                    }
                }
            }


        }

    }
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(12.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 15.dp)
                    .fillMaxWidth(),
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