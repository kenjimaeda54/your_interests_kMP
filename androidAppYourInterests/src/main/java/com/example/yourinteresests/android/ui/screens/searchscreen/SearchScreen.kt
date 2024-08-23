package com.example.yourinteresests.android.ui.SearchScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.searchscreen.view.RowInformationPlace
import com.example.yourinteresests.android.ui.screens.searchscreen.view.RowInformationShimmer
import com.example.yourinteresests.android.utils.ComposableLifecycle
import com.example.yourinteresests.android.utils.StackScreens
import com.example.yourinterest.data.model.Coordinates
import com.example.yourinterest.viewmodel.SearchPlacesByQueryViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen( location: Coordinates, navController: NavHostController) {
    var searchPlace by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val placesByQueryViewModel = viewModel<SearchPlacesByQueryViewModel>()
    val listPlaces by placesByQueryViewModel.placesByQuery.collectAsState()



    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            placesByQueryViewModel.getPlacesByQuery(
                query = "",
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Column(modifier = Modifier.padding(horizontal = 13.dp, vertical = 20.dp)) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                value = searchPlace,
                singleLine = false,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None
                ),
                textStyle = TextStyle(
                    fontFamily = fontsKulimPark,
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp,
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
                onValueChange = {
                    searchPlace = it
                    placesByQueryViewModel.getPlacesByQuery(
                        query = it.text,
                        latitude = location.latitude,
                        longitude = location.longitude
                    )


                }) {
                OutlinedTextFieldDefaults.DecorationBox(
                    value = searchPlace.text,
                    innerTextField = it,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Icon Search",
                            tint = MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Pesquisar",
                            fontFamily = fontsKulimPark,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f)
                        )
                    },
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(
                            enabled = true,
                            isError = false,
                            interactionSource = interactionSource,
                            shape = RoundedCornerShape(CornerSize(7.dp)),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(
                                    0.5f
                                ),
                                focusedContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                                disabledBorderColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(0.5f),
                                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(0.5f),

                                ),
                            focusedBorderThickness = 0.dp,
                            unfocusedBorderThickness = 0.dp
                        )
                    }
                )
            }
            Text(
                text = "Pesquise por: nome, categoria ou telefone",
                fontFamily = fontsKulimPark,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primaryContainer,

                )
            if (listPlaces.isLoading) {
                LazyColumn {
                    items(10) {
                        RowInformationShimmer(
                            modifier = Modifier.padding(
                                bottom = 15.dp,
                                top = if (it == 0) 30.dp else 0.dp
                            ),
                        )
                    }
                }
            } else if (listPlaces.data != null && listPlaces.data?.isEmpty() == false) {
                LazyColumn {
                    items(listPlaces.data!!.size) {
                        RowInformationPlace(
                            modifier = Modifier.padding(
                                bottom = 15.dp,
                                top = if (it == 0) 30.dp else 0.dp
                            ).clickable {
                                navController.navigate(route = StackScreens.DetailsPlace.name + "/${listPlaces.data!![it].fsqId}")
                            },
                            place = listPlaces.data!![it]
                        )
                    }
                }
            } else {
               Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                   Text(
                       text = "Nenhum resultado encontrado",
                       fontFamily = fontsKulimPark,
                       fontWeight = FontWeight.SemiBold,
                       fontSize = 13.sp,
                       color = MaterialTheme.colorScheme.primaryContainer
                   )
               }
            }
        }
    }
}
