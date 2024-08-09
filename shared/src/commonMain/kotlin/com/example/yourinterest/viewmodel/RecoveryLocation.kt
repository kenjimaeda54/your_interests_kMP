package com.example.yourinterest.viewmodel

 import com.example.yourinterest.data.DataOrException
 import com.example.yourinterest.model.Coordinates
 import com.example.yourinterest.repository.RecoveryLocationRepository
 import com.example.yourinterest.util.CoroutineViewModel
 import com.example.yourinterest.util.GeolocationException
 import kotlinx.coroutines.flow.MutableStateFlow
 import kotlinx.coroutines.flow.StateFlow
 import kotlinx.coroutines.flow.launchIn
 import kotlinx.coroutines.flow.onEach
 import kotlinx.coroutines.launch
 import org.koin.core.component.KoinComponent
 import org.koin.core.component.inject

class RecoveryLocation: CoroutineViewModel(), KoinComponent {
    private  val recoveryLocationRepository: RecoveryLocationRepository by inject()
    private  val  _location = MutableStateFlow<DataOrException<Coordinates, GeolocationException,Boolean>>(DataOrException(data = null, isLoading = true, exception = null))
    val location: StateFlow<DataOrException<Coordinates, GeolocationException,Boolean>> = _location
    val tryRecoveryData = MutableStateFlow(0)


    fun getLocation() {
        scope.launch {
            if (tryRecoveryData.value < 4){
                //nao colocar no iniit do viewMOdel pois vai ficar em loop
                _location.value = recoveryLocationRepository.fetchLocation()
                tryRecoveryData.value++
                getLocation()
            }


          }
     }

    fun observeRecoveryData(onchange: (Int) -> Unit){
        tryRecoveryData.onEach {
            onchange(it)
        }.launchIn(scope)
    }



}