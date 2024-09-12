package com.exampl.yourinteresests.android.ui

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yourinteresests.android.theme.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinterest.data.model.user.UserModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(user: UserModel) {
   Surface(
       modifier = Modifier.fillMaxSize(),
       color = MaterialTheme.colorScheme.onPrimaryContainer
   ) {
       Column(
           modifier = Modifier.fillMaxSize().padding(horizontal = 13.dp, vertical = 20.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ) {
           AsyncImage(
               modifier = Modifier
                   .size(150.dp)
                   .clip(CircleShape),
               model = ImageRequest.Builder(LocalContext.current).data(user.photoUrl).build(),
               contentDescription = "Profile Image",
           )
           Column(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), horizontalAlignment = Alignment.Start) {
               Row {
                   Text(
                       "Nome: ",
                       fontFamily = fontsKulimPark,
                       fontWeight = FontWeight.Light,
                       fontSize = 20.sp,
                       color = MaterialTheme.colorScheme.primaryContainer
                   )

                   Text(
                       text = user.name,
                       fontFamily = fontsKulimPark,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp,
                       color = MaterialTheme.colorScheme.primaryContainer
                   )
               }
               Row {
                   Text(
                       "Telefone: ",
                       fontFamily = fontsKulimPark,
                       fontWeight = FontWeight.Light,
                       fontSize = 20.sp,
                       color = MaterialTheme.colorScheme.primaryContainer
                   )
                   Text(
                       text = user.phone,
                       fontFamily = fontsKulimPark,
                       fontWeight = FontWeight.Bold,
                       fontSize = 20.sp,
                       color = MaterialTheme.colorScheme.primaryContainer
                   )
               }

           }
       }
   }

}

