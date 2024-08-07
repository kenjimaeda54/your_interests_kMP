package com.example.yourinterest.repository

import com.example.yourinterest.client.GeolocationError
import com.example.yourinterest.client.RecoveryLocationClient
import com.example.yourinterest.data.DataOrException
import com.example.yourinterest.model.Location
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecoveryLocationRepository : KoinComponent {
    private val recoveryClient: RecoveryLocationClient by inject()


    suspend fun fetchLocation(): DataOrException<Location, GeolocationError, Boolean> {
        val result = recoveryClient.getLocation()
        return if (result.first != null) DataOrException(
            data = result.first,
            isLoading = false,
            exception = null
        ) else DataOrException(data = null, isLoading = false, exception = result.second)

    }

}