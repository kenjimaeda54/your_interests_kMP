package com.example.yourinterest.data.model.user

import kotlinx.serialization.Serializable


@Serializable
data class UserEntityResponse(
    val id: String,
    val name: String,
    val phone: String,
    val photoUrl: String
)

