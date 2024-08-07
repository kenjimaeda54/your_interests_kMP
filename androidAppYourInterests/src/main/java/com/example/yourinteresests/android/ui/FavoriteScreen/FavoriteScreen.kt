package com.example.yourinteresests.android.ui.FavoriteScreen

//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.example.yourinteresests.android.utils.ComposableLifecycle
import com.example.yourinterest.client.GeolocationError
import com.example.yourinterest.viewmodel.RecoveryLocation
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions


//https://github.com/mapbox/mapbox-maps-android/blob/extension-compose-v0.1.0/extension-compose/README.md?source=post_page-----408cbea98866--------------------------------#add-viewannotation-to-the-map

@OptIn(MapboxExperimental::class)
@Composable
fun FavoriteScreen() {
    val recoveryLocationModel = remember {
        RecoveryLocation()
    } //precisa do remember
    val location by recoveryLocationModel.location.collectAsState()


    ComposableLifecycle {_,event ->
        if (event == Lifecycle.Event.ON_CREATE){
            recoveryLocationModel.getLocation()
        }

    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(-46.6492,-23.5681))
            zoom(13.0)
            pitch(0.0)
            bearing(0.0)
        }
    }


    if (location.isLoading){

        Text(text = "loading")

    } else if(location.exception  != null){
        if(location.exception == GeolocationError.PermissionError) Text(text = "Aceita as permissoes") else Text(text = "Erro com gps")

    } else {
        MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapViewportState,

            style = {
                MapStyle(style = Style.SATELLITE_STREETS)
            }
        ) {
            ViewAnnotation(options = viewAnnotationOptions {
                allowOverlap(false)
                geometry(Point.fromLngLat(-46.6492,-23.5681))
            }) {
                Image(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = com.example.yourinteresests.android.R.drawable.pin),
                    contentDescription = "Image marker",
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
                )
                Text(modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 7.dp)
                    .background(
                        MaterialTheme.colorScheme.tertiary
                    ),text = "${location.data?.coordinates?.latitude} ${location.data?.coordinates?.longitude}", color = MaterialTheme.colorScheme.onTertiary)
            }
        }


    }



  }