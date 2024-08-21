package com.example.yourinterest.viewmodel

import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel
import com.example.yourinterest.data.repository.SearchPlacesByQueryRepository
import com.example.yourinterest.util.CoroutineViewModel
import com.example.yourinterest.util.DataOrException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchPlacesByViewModel: CoroutineViewModel(), KoinComponent{
    private  val repository: SearchPlacesByQueryRepository by inject()
    private  val _placesByQuery = MutableStateFlow<DataOrException<List<PhotosPlacesWithRelationNearbyModel>, Exception, Boolean>>(DataOrException(isLoading = true, data = null, exception = null))//<List<PhotosPlacesWithRelationNearbyModel>>()
    val placesByQuery: StateFlow<DataOrException<List<PhotosPlacesWithRelationNearbyModel>, Exception, Boolean>> = _placesByQuery
    private  var job: Job = Job()




    fun getPlacesByQuery(query: String,latitude: Double, longitude: Double)  {
        job.cancel()
        _placesByQuery.value.isLoading = true
        job =  scope.launch {
                delay(700) //tempooo ideal muito abaixo pode dar erro de job cancelado
                val result = repository.fetchPlacesByQuery(query = query, latitude = latitude, longitude = longitude)
                if(result.data != null) {
                    if(_placesByQuery.value.data == null || _placesByQuery.value.data?.isEmpty() == true) {
                        _placesByQuery.value = result
                        return@launch
                    }
                    _placesByQuery.update { currentState ->
                        currentState.copy(
                            data = result.data,    //result.data + _placesByQuery.value.data!!, estou concatenando o valro atual com o antigo usando o + , mas caso de uso nosso queremoos apenas o valor atual
                            isLoading = false,
                            exception = null
                        )
                    }
                }else {
                  _placesByQuery.value = result
                }
         }

    }
}