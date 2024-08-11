package com.example.yourinteresests.android.ui.FavoriteScreen

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.yourinterest.viewmodel.RecoveryLocation
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.ComposeMapInitOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
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
    val address by recoveryLocationModel.address.collectAsState()

    ComposableLifecycle {_,event ->
        if (event == Lifecycle.Event.ON_CREATE){
            recoveryLocationModel.getLocation()
        }

    }




    if (location.isLoading){

        Text(text = "loading")

    } else if(location.exception  != null){
        if(location.exception!!.error == GeolocationError.PermissionError) Text(text = "Aceita as permissoes") else Text(text = "Erro com gps")

    } else {
        MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = MapViewportState(
                initialCameraState = CameraState(
                    Point.fromLngLat(location.data!!.longitude,location.data!!.latitude),
                    EdgeInsets(0.0, 0.0, 0.0, 0.0),
                    13.0,
                    0.0,
                    0.0

                )
            ),

            style = {
                MapStyle(style = Style.SATELLITE_STREETS)
            }
        ) {
            ViewAnnotation(options = viewAnnotationOptions {
                allowOverlap(false)
                geometry(Point.fromLngLat(location.data!!.longitude,location.data!!.latitude))
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                 ){
                    Image(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = com.example.yourinteresests.android.R.drawable.pin),
                    contentDescription = "Image marker",
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.error)
                )
                    Text(modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(corner = CornerSize(10.dp))
                        )
                        .padding(5.dp)
                        ,text = address, color = MaterialTheme.colorScheme.onTertiary,
                        fontFamily = fontsKulimPark,
                        fontWeight = FontWeight.Light,
                        fontSize = 13.sp)

                }
            }
        }


    }



  }