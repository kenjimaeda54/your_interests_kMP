package com.example.yourinteresests.android.ui.screens.proflescreen.FavoriteScreen

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.utils.ComposableLifecycle
import com.example.yourinterest.util.GeolocationError
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel
import com.example.yourinterest.viewmodel.RecoveryLocationViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import com.spr.jetpack_loading.components.indicators.CircularPulsatingIndicator


//https://github.com/mapbox/mapbox-maps-android/blob/extension-compose-v0.1.0/extension-compose/README.md?source=post_page-----408cbea98866--------------------------------#add-viewannotation-to-the-map

@OptIn(MapboxExperimental::class)
@Composable
fun  NearbyInterests() {
    val recoveryLocationViewModelModel = viewModel<RecoveryLocationViewModel>()
    val nearbyViewModel = viewModel<PlacesNearbyViewModel>()
    val location by recoveryLocationViewModelModel.location.collectAsState()
    val address by recoveryLocationViewModelModel.address.collectAsState()
    val dataPlacesNearby by nearbyViewModel.placesNearby.collectAsState()

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            recoveryLocationViewModelModel.getLocation()
        }

    }


    if (location.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            //https://github.com/MahboubehSeyedpour/jetpack-loading
            CircularPulsatingIndicator(
                color = MaterialTheme.colorScheme.secondary,
                penThickness = 10.dp,
                animationDuration = 1200
            )
        }
    } else if (location.exception != null) {
        if (location.exception!!.error == GeolocationError.PermissionError) Text(text = "Aceita as permissoes") else Text(
            text = "Erro com gps"
        )

    } else {
        nearbyViewModel.getPlacesNearby(
            latitude = location.data!!.latitude,
            longitude = location.data!!.longitude
        )
        MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = MapViewportState(
                initialCameraState = CameraState(
                    Point.fromLngLat(location.data!!.longitude, location.data!!.latitude),
                    EdgeInsets(0.0, 0.0, 0.0, 0.0), 17.0,
                    0.0,
                    0.0

                ),

                ),
            style = {
                //usa o maapbox studio para alterar os estilos
                //removi pontos de interesse usando o mapbox studio
                MapStyle(style = "mapbox://styles/kenjimaeda/clzu6cgv300qe01pd35jr9y81")
            }
        ) {
            ViewAnnotation(options = viewAnnotationOptions {
                allowOverlap(true)
                geometry(Point.fromLngLat(location.data!!.longitude, location.data!!.latitude))
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Image(
                        modifier = Modifier.size(35.dp),
                        painter = painterResource(id = com.example.yourinteresests.android.R.drawable.pin),
                        contentDescription = "Image marker",
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.error)
                    )
                    Text(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.tertiary,
                                shape = RoundedCornerShape(corner = CornerSize(10.dp))
                            )
                            .padding(5.dp),
                        text = address,
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp
                    )

                }

            }

            if (dataPlacesNearby.data != null) {
                val dataPlaces = dataPlacesNearby.data ?: listOf()
                //vai dar erro de LayoutNode se envolver o lazyColumn com verificao, precisamos garatnri que tenha data
                //para poder chamar o lazyColumn
                if (dataPlaces.isNotEmpty()) {
                    dataPlaces.forEach {

                        ViewAnnotation(options = viewAnnotationOptions {
                            allowOverlap(false)
                            geometry(
                                Point.fromLngLat(
                                    it.places.geocode.longitude,
                                    it.places.geocode.latitude
                                )
                            )
                        }) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                if (it.places.name.isEmpty())
                                    Box(modifier = Modifier)
                                else
                                    AsyncImage(
                                        model =
                                        ImageRequest.Builder(context = LocalContext.current)
                                            .data(it.photoPlacesModel.icon)
                                            .build(),
                                        contentDescription = "Image marker",
                                        contentScale = ContentScale.FillBounds,
                                        filterQuality = FilterQuality.High,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(
                                                CircleShape
                                            )
                                    )
                                Text(
                                    text = it.places.name,
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.tertiary,
                                            shape = RoundedCornerShape(corner = CornerSize(10.dp))
                                        )
                                        .padding(5.dp),
                                    color = MaterialTheme.colorScheme.onTertiary,
                                    fontFamily = fontsKulimPark,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 13.sp

                                )
                            }
                        }
                    }


                }

            }

        }


    }

}