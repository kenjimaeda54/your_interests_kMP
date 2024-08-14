package com.example.yourinterest.viewmodel

import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel
import com.example.yourinterest.data.repository.PlacesNearbyRepository
import com.example.yourinterest.util.CoroutineViewModel
import com.example.yourinterest.util.DataOrException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


//https://stackoverflow.com/questions/65001729/populate-and-emit-stateflow-list
//adicionar ou remover mutablestate
//https://kotlinlang.org/docs/collection-plus-minus.html
class PlacesNearbyViewModel: CoroutineViewModel(), KoinComponent {
    private  val repository: PlacesNearbyRepository by inject()
    private val _placesNearby = MutableStateFlow<DataOrException<List<PhotosPlacesWithRelationNearbyModel>, Exception, Boolean>>(DataOrException(data = listOf(), exception = null, isLoading = true))
    val placesNearby: StateFlow<DataOrException<List<PhotosPlacesWithRelationNearbyModel>, Exception, Boolean>> = _placesNearby


    fun getPlacesNearby(latitude: Double,longitude: Double)  {
        scope.launch {
            _placesNearby.value = repository.fetchPlacesNearby(latitude = latitude, longitude = longitude)
        }
     }


}