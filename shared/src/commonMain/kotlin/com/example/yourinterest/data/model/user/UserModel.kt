package com.example.yourinterest.data.model.user

data class UserModel(
    val id: Int,
    val name: String,
    val phone: String,
    val photoUrl: String
)

data class  UserWithPhotoByTeArray(
    var name: String,
    val phone: String,
    var photo: ByteArray
)

fun UserEntityResponse.toUserModel() = UserModel(
    id = id,
    name = name,
    photoUrl = photoUrl,
    phone = phone
)

