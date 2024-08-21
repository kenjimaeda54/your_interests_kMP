package com.example.yourinterest.data.repository

import com.example.yourinterest.data.client.PlacesNearbyClient
import com.example.yourinterest.data.client.SearchPlacesByQueryClient
import com.example.yourinterest.data.model.photosrelationswithplace.PhotoPlacesModel
import com.example.yourinterest.data.model.photosrelationswithplace.PhotosPlacesWithRelationNearbyModel
import com.example.yourinterest.data.model.photosrelationswithplace.toPhotoPlacesModel
import com.example.yourinterest.data.model.photosrelationswithplace.toPlacesNearbyModel
import com.example.yourinterest.util.DataOrException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchPlacesByQueryRepository : KoinComponent {
    private val clientSearchPlacesByQuery: SearchPlacesByQueryClient by inject()
    private val clientPlacesNearby: PlacesNearbyClient by inject()

    suspend fun fetchPlacesByQuery(
        query: String,
        latitude: Double,
        longitude: Double
    ): DataOrException<List<PhotosPlacesWithRelationNearbyModel>, Exception, Boolean> {
        val result = clientSearchPlacesByQuery.fetchPlacesByQuery(
            query = query,
            latitude = latitude,
            longitude = longitude
        )
        if (result.data != null) {
            if (result.data.results.isEmpty()) {
                return DataOrException(data = listOf(), exception = null, isLoading = false)
            }
            val placesByQuery = result.data.results.map { it.toPlacesNearbyModel() }


            val photosPLacesNearby: List<PhotosPlacesWithRelationNearbyModel> =
                placesByQuery.map { place ->
                    val resultPlaces = clientPlacesNearby.fetchPhotos(place.fsqId)
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
                            fsqId = "3434343"
                        )
                    }


                }
            return DataOrException(data = photosPLacesNearby, exception = null, isLoading = false)
        }

        return DataOrException(data = null, exception = null, isLoading = false)


    }

}