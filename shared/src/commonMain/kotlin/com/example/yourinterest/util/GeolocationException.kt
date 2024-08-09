package com.example.yourinterest.util

import com.example.yourinterest.model.Coordinates


enum class  GeolocationError {
    PermissionError,
    LocationError
}

data class GeolocationException(
    val error: GeolocationError?,
    val messageError: String?,
 )