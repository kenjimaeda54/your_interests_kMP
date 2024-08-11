package com.example.yourinterest.repository

import com.example.yourinterest.client.RecoveryLocationClient
import com.example.yourinterest.data.DataOrException
import com.example.yourinterest.model.Coordinates
import com.example.yourinterest.util.GeolocationException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecoveryLocationRepository : KoinComponent {
    private val recoveryClient: RecoveryLocationClient by inject()


    suspend fun fetchLocation(): DataOrException<Coordinates, GeolocationException, Boolean> {
        val result = recoveryClient.getLocation()
        return if (result.first != null) DataOrException(
            data = result.first,
            isLoading = false,
            exception = null
        ) else DataOrException(data = null, isLoading = false, exception = result.second)

    }

    suspend fun  fetchReverseLocation(latitude: Double,longitude: Double): String {
       return recoveryClient.getReverseLocation(latitude,longitude)
    }


}