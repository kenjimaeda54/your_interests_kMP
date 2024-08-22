package com.example.yourinterest.data.repository

import com.example.yourinterest.data.client.PlacesNearbyClient
import com.example.yourinterest.data.model.photosrelationswithplace.PhotoPlacesModel
import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel
import com.example.yourinterest.data.model.photosrelationswithplace.toPhotoPlacesModel
import com.example.yourinterest.data.model.photosrelationswithplace.toPlacesNearbyModel
import com.example.yourinterest.util.DataOrException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextInt

class PlacesNearbyRepository : KoinComponent {
    private val clientPlacesNearby: PlacesNearbyClient by inject()


    suspend fun fetchPlacesNearby(
        latitude: Double,
        longitude: Double
    ): DataOrException<List<PhotosPlacesWithRelationNearbyModel>, Exception, Boolean> {
        val result =
            clientPlacesNearby.fetchPlacesNearby(latitude = latitude, longitude = longitude)
        if (result.data != null) {
            if (result.data.results.isEmpty()) {
                return DataOrException(data = listOf(), exception = null, isLoading = false)
            }
            val placesNearby = result.data.results.map { it.toPlacesNearbyModel() }

            val photosPlacesNearby: List<PhotosPlacesWithRelationNearbyModel> =
                placesNearby.map { place ->
                    val resultPlaces = clientPlacesNearby.fetchPhotos(fsqId = place.fsqId)
                    if (resultPlaces.data != null) {
                        PhotosPlacesWithRelationNearbyModel(
                            places = place,
                            photoPlacesModel = if (resultPlaces.data.isEmpty()) PhotoPlacesModel(
                                id = "",
                                icon = ""
                            ) else resultPlaces.data.map { it.toPhotoPlacesModel() }.first(),
                            fsqId = place.fsqId
                        )
                    } else {
                        PhotosPlacesWithRelationNearbyModel(
                            places = place,
                            photoPlacesModel = PhotoPlacesModel(id = "", icon = ""),
                            fsqId =  place.fsqId
                        )
                    }


                }

            return DataOrException(data = photosPlacesNearby, exception = null, isLoading = false)

        }
        return DataOrException(data = null, exception = result.exception, isLoading = false)

    }

}