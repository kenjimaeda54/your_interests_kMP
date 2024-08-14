package com.example.yourinterest.data.model.photosrelationswithplace

import kotlinx.serialization.Serializable


@Serializable
data class PlacesNearbyEntity(
    val results: List<PlacesNearbyResultResponse>

)

@Serializable
data class PlacesNearbyResultResponse(
    val fsq_id: String,
    val geocodes: Geocodes,
    val distance: Int,
    val location: Location,
    val name: String

)



@Serializable
data class Icon(
    val prefix: String,
    val suffix: String
)

@Serializable
data class  Geocodes(
    val main: GeocodesMain
)

@Serializable
data class  GeocodesMain(
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class  Location(
    val formatted_address: String
)




@Serializable
data class PhotoPlacesEntityResponse(
    val id: String,
    val prefix: String,
    val suffix: String
)