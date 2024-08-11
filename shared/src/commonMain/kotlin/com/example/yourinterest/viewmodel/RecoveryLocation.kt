package com.example.yourinterest.viewmodel

 import com.example.yourinterest.data.DataOrException
 import com.example.yourinterest.model.Coordinates
 import com.example.yourinterest.repository.RecoveryLocationRepository
 import com.example.yourinterest.util.CoroutineViewModel
 import com.example.yourinterest.util.GeolocationException
 import kotlinx.coroutines.flow.MutableStateFlow
 import kotlinx.coroutines.flow.StateFlow
 import kotlinx.coroutines.launch
 import org.koin.core.component.KoinComponent
 import org.koin.core.component.inject


class  RecoveryLocation: CoroutineViewModel(), KoinComponent {
    private  val recoveryLocationRepository: RecoveryLocationRepository by inject()
    private  val  _location = MutableStateFlow<DataOrException<Coordinates, GeolocationException,Boolean>>(DataOrException(data = null, isLoading = true, exception = null))
    private  val _address = MutableStateFlow<String>("")
    val location: StateFlow<DataOrException<Coordinates, GeolocationException,Boolean>> = _location
    val address: StateFlow<String> = _address



    fun getLocation() {
        scope.launch {
                //nao colocar no iniit do viewMOdel pois vai ficar em loop
             _location.value= recoveryLocationRepository.fetchLocation()
                if(location.value.data != null) {
                     getReverseGeolocation(latitude =_location.value.data!!.latitude, longitude = location.value.data!!.longitude)
                }

           }
     }

    private fun getReverseGeolocation(latitude: Double,longitude: Double) {
        scope.launch {
            _address.value = recoveryLocationRepository.fetchReverseLocation(latitude = latitude, longitude = longitude)
        }
    }



}