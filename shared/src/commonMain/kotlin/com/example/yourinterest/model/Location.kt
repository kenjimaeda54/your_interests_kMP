package com.example.yourinterest.model



data class Coordinates (
     val longitude: Double,
     val latitude: Double
)

data class Location(
     val coordinates: Coordinates,
     val accuracy: Double,
     val timestampMillis: Long,
)
