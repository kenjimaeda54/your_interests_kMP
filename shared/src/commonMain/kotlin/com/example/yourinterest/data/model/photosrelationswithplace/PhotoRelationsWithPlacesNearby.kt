package com.example.yourinterest.data.model.photosrelationswithplace


data class PlacesNearbyModel(
    val geocode: GeocodeModel,
    val address: String,
    val name: String,
    val distance: Int,
    val fsqId: String
)



data class  GeocodeModel(
    val latitude: Double,
    val longitude: Double
)




fun PlacesNearbyResultResponse.toPlacesNearbyModel() = PlacesNearbyModel(
    fsqId = fsq_id,
    name = name,
    distance = distance,
    geocode = GeocodeModel(
        latitude = geocodes.main.latitude,
        longitude =  geocodes.main.longitude
    ),
    address = location.formatted_address
)



data class PhotoPlacesModel(
    val id: String,
    val icon: String
)

data class  PhotosPlacesWithRelationNearbyModel(
    val photoPlacesModel: PhotoPlacesModel,
    val places: PlacesNearbyModel
)

fun PhotoPlacesEntityResponse.toPhotoPlacesModel(): PhotoPlacesModel {
    return PhotoPlacesModel(
        id = id,
        icon = "${prefix}original${suffix}"
    )
}