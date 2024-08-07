package com.example.yourinterest.data


data class DataOrException<T, Exception,Boolean> (
    val data: T? = null,
    val exception: Exception? = null,
    var isLoading: Boolean
)