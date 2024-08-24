package com.example.yourinteresests.android.ui.screens.view

import android.location.Location
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yourinteresests.android.theme.fontsKulimPark
import com.example.yourinterest.data.model.Coordinates
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

@OptIn(MapboxExperimental::class)
@Composable
fun CustomMapBox(modifier: Modifier = Modifier, pointCamera: Coordinates, pointAnnotation: Coordinates, address: String, content: @Composable () -> Unit = {}) {
    MapboxMap(
        Modifier.fillMaxSize(),
        mapViewportState = MapViewportState(
            initialCameraState = CameraState(
                Point.fromLngLat(pointCamera.longitude, pointCamera.latitude),
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
    ){
        ViewAnnotation(options = viewAnnotationOptions {
            allowOverlap(true)
            geometry(Point.fromLngLat(pointAnnotation.longitude,pointAnnotation.latitude))
        }) {
            Column(
                modifier = modifier,
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
        content()
    }
}