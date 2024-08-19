package com.example.yourinteresests.android.ui.SearchScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.example.yourinteresests.android.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.searchscreen.view.RowInformationPlace
import com.example.yourinteresests.android.utils.BottomBarScreen
import com.example.yourinteresests.android.utils.ComposableLifecycle
import com.example.yourinterest.data.model.photosrelationswithplace.GeocodeModel
import com.example.yourinterest.data.model.photosrelationswithplace.PhotoPlacesModel
import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel
import com.example.yourinterest.data.model.photosrelationswithplace.PlacesNearbyModel
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(photosPlaces: List<PhotosPlacesWithRelationNearbyModel>) {
    var searchPlace by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val interactionSource = remember { MutableInteractionSource() }


   Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.onPrimaryContainer) {
       Column(modifier = Modifier.padding(horizontal = 13.dp, vertical = 20.dp)) {
           BasicTextField(
               modifier = Modifier
                   .fillMaxWidth()
                   .background(Color.Transparent),
               value = searchPlace,
               textStyle = TextStyle(
                   fontFamily = fontsKulimPark,
                   fontWeight = FontWeight.Normal,
                   fontSize = 17.sp,
                   color = MaterialTheme.colorScheme.primaryContainer
               ),
               onValueChange = {
                   searchPlace = it
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
                               unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f),
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
               fontWeight = FontWeight.Light,
               fontSize = 11.sp,
               color = MaterialTheme.colorScheme.primaryContainer,

               )
           LazyColumn {
               items(photosPlaces.size) {
                   RowInformationPlace(
                       modifier = Modifier.padding(bottom = 15.dp, top = if (it == 0) 30.dp else 0.dp),
                       place = photosPlaces[it]
                   )
               }
           }
       }
   }
}
