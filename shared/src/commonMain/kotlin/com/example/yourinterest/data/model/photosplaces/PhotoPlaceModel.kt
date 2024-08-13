package com.example.yourinterest.data.model.photosplaces

import com.example.yourinterest.data.model.placesnearby.PlacesNearbyModel

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