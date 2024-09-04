package com.example.yourinterest.data.model.user

import kotlinx.serialization.Serializable


@Serializable
data class UserEntityResponse(
    val id:  Int,
    val name: String,
    val phone: String,
    val photoUrl: String
)

