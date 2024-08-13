package com.example.yourinterest.data.model.photosplaces

import kotlinx.serialization.Serializable


@Serializable
data class PhotoPlacesEntityResponse(
    val id: String,
    val prefix: String,
    val suffix: String
)