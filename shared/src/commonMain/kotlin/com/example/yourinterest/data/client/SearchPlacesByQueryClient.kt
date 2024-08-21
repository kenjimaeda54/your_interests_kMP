package com.example.yourinterest.data.client

import com.example.yourinterest.data.model.photosrelationswithplace.PlacesNearbyEntity
import com.example.yourinterest.util.DataOrException
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class SearchPlacesByQueryClient(private val ktorApi: KtorApi) : KtorApi by ktorApi {

    suspend fun fetchPlacesByQuery(
        query: String,
        latitude: Double,
        longitude: Double
    ): DataOrException<PlacesNearbyEntity  , Exception, Boolean> {
        return try {
            val result = client.get("/v3/places/search?query=$query&ll=${latitude},${longitude}")
            if (result.status != HttpStatusCode.OK) {
                throw Exception("Error fetching places by query")
            }
            DataOrException(data = result.body(), exception = null, isLoading = false)
        } catch (exception: Exception) {
            DataOrException(data = null, exception = exception, isLoading = false)
        }
    }


}