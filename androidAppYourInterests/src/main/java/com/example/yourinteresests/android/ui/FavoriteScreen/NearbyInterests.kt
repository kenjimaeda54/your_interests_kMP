package com.example.yourinteresests.android.ui.FavoriteScreen

//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinteresests.android.utils.ComposableLifecycle
import com.example.yourinterest.util.GeolocationError
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel
import com.example.yourinterest.viewmodel.RecoveryLocation
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.Style
import com.mapbox.maps.StyleLoaded
import com.mapbox.maps.StylePackLoadOptions
import com.mapbox.maps.extension.compose.ComposeMapInitOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.extension.compose.style.ImportConfigs
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.styleImportsConfig
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import kotlin.coroutines.coroutineContext


//https://github.com/mapbox/mapbox-maps-android/blob/extension-compose-v0.1.0/extension-compose/README.md?source=post_page-----408cbea98866--------------------------------#add-viewannotation-to-the-map

@OptIn(MapboxExperimental::class)
@Composable
fun FavoriteScreen() {
    val recoveryLocationModel = remember {
        RecoveryLocation()
    } //precisa do remember
    val nearbyViewModel = remember {
        PlacesNearbyViewModel()
    }

    val location by recoveryLocationModel.location.collectAsState()
    val address by recoveryLocationModel.address.collectAsState()
    val dataPlacesNearby by nearbyViewModel.placesNearby.collectAsState()

    ComposableLifecycle { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            recoveryLocationModel.getLocation()
        }

    }




    if (location.isLoading) {

        Text(text = "loading")

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
                    EdgeInsets(0.0, 0.0, 0.0, 0.0),
                    13.0,
                    0.0,
                    0.0

                ),

                ),
            style = {
                //usa o maapbox studio para alterar os estilos
                //removi pontos de interesse usando o mapbox studio
                MapStyle(style = "mapbox://styles/kenjimaeda/clhnjjmrg039d01pa3bz482gg")
            }
        ) {
            ViewAnnotation(options = viewAnnotationOptions {
                allowOverlap(false)
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

                        if (it.places.category.id.isNotEmpty()) {


                            ViewAnnotation(options = viewAnnotationOptions {
                                allowOverlap(false)
                                geometry(
                                    Point.fromLngLat(
                                        it.places.geocode.longitude,
                                        it.places.geocode.latitude
                                    )
                                )
                            }) {
                                Box {
                                    Box {
                                        Text(text = it.photoPlacesModel.icon)
                                    }
                                    Box {
                                        Text(text = it.places.category.shortName)
                                    }
                                }
                            }
                        }

                    }
                }

            }

        }


    }

}