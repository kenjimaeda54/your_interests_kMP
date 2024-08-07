package com.example.yourinterest.client

import com.example.yourinterest.model.Coordinates
import com.example.yourinterest.model.Location
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.TrackingStatus
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.map


enum class  GeolocationError {
    PermissionError,
    LocationError
}


class RecoveryLocationClient {
    private  val geolocation: Geolocator = Geolocator.mobile()

    suspend fun getLocation(): Pair<Location?, GeolocationError?> {
        val resultGeolocation = geolocation.current(priority = Priority.HighAccuracy)
        return when(resultGeolocation){
            is GeolocatorResult.Success ->
                Pair(Location(Coordinates(resultGeolocation.data.coordinates.longitude,resultGeolocation.data.coordinates.latitude),resultGeolocation.data.accuracy,resultGeolocation.data.timestampMillis), null)
            is GeolocatorResult.Error -> when(resultGeolocation) {
                is GeolocatorResult.PermissionError -> Pair(null, GeolocationError.PermissionError)
                else ->  Pair(null,  GeolocationError.LocationError)
            }
        }
     }

}