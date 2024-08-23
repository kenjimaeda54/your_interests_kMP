package com.example.yourinteresests.android.ui.screens.detailsplace

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.ui.screens.detailsplace.view.ItemIconInformation
import com.example.yourinterest.data.model.Coordinates
import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel


@Composable
fun DetailsPlace(place: PhotosPlacesWithRelationNearbyModel, location: Coordinates) {
    val configuration = LocalConfiguration.current



    fun shouldReturnDistance(distance: Int): String {
        if (distance > 1000) {
            return  String.format("%.2f", distance.toFloat() / 1000) + " km"
        }
        return "$distance m"

    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimaryContainer
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(configuration.screenHeightDp.dp * 0.3f)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(place.photoPlacesModel.icon).build(),
                contentDescription = "Image Place",
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 13.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = place.places.name,
                    style = TextStyle(
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                Divider(modifier = Modifier.padding(top = 30.dp, bottom = 20.dp))
                ItemIconInformation(subtitle = place.places.address, information = "Endereço") {
                    Image(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Pin locaiton",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                }
                ItemIconInformation(
                    subtitle = shouldReturnDistance(place.places.distance),
                    information = "Distancia"
                ) {
                    Image(
                        modifier = Modifier.size(33.dp),
                        painter = painterResource(id = com.example.yourinteresests.android.R.drawable.distance),
                        contentDescription = "Distance Icon",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                }
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth().clip(RoundedCornerShape(13.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://api.mapbox.com/styles/v1/mapbox/streets-v10/static/pin-s-l+3C6E71(${location.longitude},${location.latitude})/${location.longitude},${location.latitude},14/600x600?access_token=sk.eyJ1Ijoia2VuamltYWVkYSIsImEiOiJjbTA2bWJ0c3QwNXpmMmxvcWtvaHRnZ2pwIn0.aMeRjYcDf3sJuaPYRNGqZw")
                        .build(),
                    contentDescription = "Image map",
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}

