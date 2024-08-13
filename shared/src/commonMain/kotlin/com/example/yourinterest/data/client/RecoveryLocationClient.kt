package com.example.yourinterest.data.client

import com.example.yourinterest.data.model.Coordinates
import com.example.yourinterest.util.GeolocationError
import com.example.yourinterest.util.GeolocationException
import dev.jordond.compass.Priority
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geocoder.placeOrNull
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import dev.jordond.compass.geolocation.Locator
import dev.jordond.compass.geolocation.mobile
import dev.jordond.compass.geolocation.mobile.MobileLocator
import dev.jordond.compass.geolocation.mobile.mobile
import dev.jordond.compass.permissions.LocationPermissionController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob


class RecoveryLocationClient {

    suspend fun getLocation(): Pair<Coordinates?, GeolocationException?> {
        val locator: Locator = Locator.mobile()
        val geolocation: Geolocator = Geolocator(locator, dispatcher = Dispatchers.IO)
        return when (val resultGeolocation = geolocation.current(Priority.HighAccuracy)) {
            is GeolocatorResult.GeolocationFailed -> Pair(null, GeolocationException(error = GeolocationError.LocationError, messageError = resultGeolocation.message))
            is GeolocatorResult.NotFound -> Pair(null, GeolocationException(error = GeolocationError.LocationError, messageError = resultGeolocation.message))
            is GeolocatorResult.NotSupported -> Pair(null, GeolocationException(error = GeolocationError.LocationError, messageError = resultGeolocation.message))
            is GeolocatorResult.Success -> Pair(Coordinates(resultGeolocation.data.coordinates.longitude, resultGeolocation.data.coordinates.latitude), null)
            is GeolocatorResult.Error -> when (resultGeolocation) {
                is GeolocatorResult.PermissionError ->  Pair(null, GeolocationException(error = GeolocationError.PermissionError, messageError = resultGeolocation.message))
                is GeolocatorResult.PermissionDenied ->  Pair(null, GeolocationException(error = GeolocationError.PermissionError, messageError = resultGeolocation.message))
                else -> Pair(null, GeolocationException(error = GeolocationError.LocationError, messageError = resultGeolocation.message))

            }
        }

    }

    suspend fun getReverseLocation(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder()
        val reverseGeocoder = geocoder.placeOrNull(latitude, longitude) ?: return ""
        return "${reverseGeocoder.thoroughfare},${reverseGeocoder.subThoroughfare}"
    }
}