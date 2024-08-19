package com.example.yourinteresests.android.ui.screens.searchscreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yourinteresests.android.YourInterestTheme
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinterest.data.model.photosrelationswithplace.GeocodeModel
import com.example.yourinterest.data.model.photosrelationswithplace.PhotoPlacesModel
import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel
import com.example.yourinterest.data.model.photosrelationswithplace.PlacesNearbyModel

@Composable
fun RowInformationPlace(modifier: Modifier = Modifier, place: PhotosPlacesWithRelationNearbyModel) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        AsyncImage(
            modifier = Modifier.clip(RoundedCornerShape(10.dp)).size(40.dp),
            model = ImageRequest.Builder(LocalContext.current).data(place.photoPlacesModel.icon)
                .build(),
            contentDescription = "Image Place",
            contentScale = ContentScale.FillBounds,
        )
        Text(
            text = place.places.name,
            fontFamily = fontsKulimPark,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Composable
@Preview
fun RowInformationPlacePreview() {
    YourInterestTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            RowInformationPlace(
                place = PhotosPlacesWithRelationNearbyModel(
                    photoPlacesModel = PhotoPlacesModel(
                        id = "",
                        icon = "https://github/kenjimaeda54.png"
                    ),
                    places = PlacesNearbyModel(
                        geocode = GeocodeModel(
                            latitude = 0.0,
                            longitude = 0.0
                        ),
                        name = "Casa do parafuso",
                        address = "",
                        distance = 13,
                        fsqId = ""
                    )
                )
            )
        }
    }
}