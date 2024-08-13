package com.example.yourinterest.data.client

import com.example.yourinterest.data.model.photosplaces.PhotoPlacesEntityResponse
import com.example.yourinterest.util.DataOrException
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers

class PhotosPlaceClient(private val ktorApi: KtorApi) : KtorApi by ktorApi {


    suspend fun fetchPhotos(fsqId: String): DataOrException<List<PhotoPlacesEntityResponse>, Exception, Boolean> {
        return try {
            val result = client.get("/v3/places/${fsqId}/photos")
            if (result.status != HttpStatusCode.OK) {
                throw Exception("Error fetching photos")
            }
             DataOrException(data = result.body(), exception = null, isLoading = false)
        } catch (exception: Exception) {
             DataOrException(data = null, exception = exception, isLoading = false)
        }
    }


}