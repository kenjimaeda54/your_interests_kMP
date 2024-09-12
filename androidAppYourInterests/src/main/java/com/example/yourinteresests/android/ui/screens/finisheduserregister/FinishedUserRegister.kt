package com.example.yourinteresests.android.ui.screens.finisheduserregister

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.model.content.ContentModel
import com.example.yourinteresests.android.theme.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.finisheduserregister.view.CustomOutlineTextField
import com.example.yourinteresests.android.utils.BottomBarScreen
import com.example.yourinteresests.android.utils.ComposableLifecycle
import com.example.yourinteresests.android.utils.StackScreens
import com.example.yourinteresests.android.view.ButtonDefault
import com.example.yourinterest.data.model.user.UserWithPhotoByTeArray
import com.example.yourinterest.viewmodel.UserSapabaseViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ujizin.camposer.CameraPreview
import com.ujizin.camposer.state.CamSelector
import com.ujizin.camposer.state.CaptureMode
import com.ujizin.camposer.state.ImageCaptureResult
import com.ujizin.camposer.state.rememberCamSelector
import com.ujizin.camposer.state.rememberCameraState
import kotlinx.coroutines.launch
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Stack

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun FinishedUserRegister(phone: String, navController: NavController) {
    val configuration = LocalConfiguration.current
    val coroutineScope = rememberCoroutineScope()
    val userViewModel = viewModel<UserSapabaseViewModel>()
    val isInterUser by userViewModel.insertIsSuccess.collectAsState()
    var userName by remember {
        mutableStateOf("")
    }
    var isClickedShowCamera by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val cameraState = rememberCameraState()
    var camSelector by rememberCamSelector(CamSelector.Front)
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    var imageURI by remember {
        mutableStateOf<Uri?>(null)
    }

    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    var userModel  by remember {
        mutableStateOf<UserWithPhotoByTeArray>(UserWithPhotoByTeArray("", phone, ByteArray(3)))
    }
    var isPhotoSelected by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = isInterUser) {
        if(isInterUser.data == true) {
            navController.navigate(BottomBarScreen.NearbyInterests.route) {
                popUpTo(StackScreens.FinishedUserRegister.name) {
                    inclusive = true
                }

            }
        }
    }

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
                                                isPhotoSelected = true

                                                imageURI = result.savedUri!!
                                                val encoded =
                                                    Files.readAllBytes(Paths.get(result.savedUri!!.path))

                                                //nao inserir o usuario aqui so apos o confirmar
                                                //tambem testar tirar foto novamente apos fechar
                                                userModel.photo = encoded
                                                isClickedShowCamera = false
                                            }

                                            is ImageCaptureResult.Error -> {
                                                Log.d("Error", "Error when take picture")
                                                isClickedShowCamera = false
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp, vertical = 50.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {

                }
                Column(
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    if(imageURI != null) {
                        AsyncImage(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .clickable {
                                    isClickedShowCamera = true
                                    if (!cameraPermission.status.isGranted) {
                                        cameraPermission.launchPermissionRequest()
                                    }
                                },
                            model = ImageRequest.Builder(LocalContext.current).data(imageURI).build(),
                            contentDescription = "Image Profile",
                            contentScale = ContentScale.FillBounds
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .clickable {
                                    isClickedShowCamera = true
                                    if (!cameraPermission.status.isGranted) {
                                        cameraPermission.launchPermissionRequest()
                                    }
                                },
                            painter = painterResource(id = com.example.yourinteresests.android.R.drawable.photo_user),
                            contentDescription = "Image profile"
                        )
                    }


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
                if(!isInterUser.isLoading) {
                    ButtonDefault(enabled = userName.isNotEmpty()  && isPhotoSelected
                        , text = "Confirmar") {
                        userViewModel.insertUser(userModel)
                    }

                }

                if(isInterUser.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(30.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            trackColor = MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
                        )
                }

            }


        }

    }
    if(modalSheetState.isVisible) {
        ModalBottomSheet(
            sheetState = modalSheetState,
            onDismissRequest = {
                coroutineScope.launch {
                    modalSheetState.hide()
                }
            }


        )  {
            ModalContent(userName, onchange = {
                if (userName.length <= 13) {
                    userName = it
                    userModel.name = it
                }
            }) {
                coroutineScope.launch {
                    modalSheetState.hide()
                }
            }
        }

    }

}

@Composable
@Preview
fun FinishedUserRegisterPreview() {
    YourInterestTheme {
        FinishedUserRegister(navController = rememberNavController(), phone = "123456789")
    }
}


@Composable
fun ModalContent(userName: String, onchange : (String) -> Unit, keyBoardAction : () -> Unit) {
    val focusRequester = FocusRequester()

    ComposableLifecycle { _,event ->
        if (event ==  Lifecycle.Event.ON_START) {
            focusRequester.requestFocus()
        }
    }


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
            onValueChange = onchange,
            keyboardAction =  { keyBoardAction() }
            )
    }
}