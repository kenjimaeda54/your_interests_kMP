package com.example.yourinteresests.android.ui.FavoriteScreen

//noinspection UsingMaterialAndMaterial3Libraries
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.ColorFilter
import android.service.autofill.ImageTransformation
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yourinteresests.R
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions


//https://github.com/mapbox/mapbox-maps-android/blob/extension-compose-v0.1.0/extension-compose/README.md?source=post_page-----408cbea98866--------------------------------#add-viewannotation-to-the-map

@OptIn(MapboxExperimental::class)
@Composable
fun FavoriteScreen() {

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(-46.6492,-23.5681))
            zoom(13.0)
            pitch(0.0)
            bearing(0.0)
        }
    }

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
          Text(modifier = Modifier.padding(horizontal = 5.dp, vertical = 7.dp).background(
              MaterialTheme.colorScheme.tertiary
          ),text = "Rua Silviano Brandao,525")
       }
    }


  }