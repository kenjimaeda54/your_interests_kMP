package com.example.yourinterest.util



data class DataOrException<T, Exception,Boolean> (
    val data: T? = null,
    val exception: Exception? = null,
    var isLoading: Boolean
)