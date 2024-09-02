package com.example.yourinterest.data.model.user

data class UserModel(
    val id: String,
    val name: String,
    val phone: String,
    val photoUrl: String
)

fun UserEntityResponse.toUserModel() = UserModel(
    id = id,
    name = name,
    photoUrl = photoUrl,
    phone = phone
)

