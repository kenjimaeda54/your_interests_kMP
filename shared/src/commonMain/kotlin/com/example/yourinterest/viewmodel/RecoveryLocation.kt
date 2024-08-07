package com.example.yourinterest.viewmodel

import com.example.yourinterest.client.GeolocationError
import com.example.yourinterest.client.RecoveryLocationClient
import com.example.yourinterest.data.DataOrException
import com.example.yourinterest.model.Location
import com.example.yourinterest.repository.RecoveryLocationRepository
import com.example.yourinterest.util.CoroutineViewModel
import dev.jordond.compass.Coordinates
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecoveryLocation: CoroutineViewModel(), KoinComponent {
    private  val recoveryLocationRepository: RecoveryLocationRepository by inject()
    private  val  _location = MutableStateFlow<DataOrException<Location, GeolocationError,Boolean>>(DataOrException(data = null, isLoading = true, exception = null))
    val location: StateFlow<DataOrException<Location, GeolocationError,Boolean>> = _location



    fun getLocation() {
        scope.launch {
            //nao colocar no iniit do viewMOdel pois vai ficar em loop
             _location.value = recoveryLocationRepository.fetchLocation()
          }
     }


}