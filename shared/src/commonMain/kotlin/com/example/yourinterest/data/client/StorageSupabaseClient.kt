package com.example.yourinterest.data.client

import com.example.yourinterest.util.DataOrException
import io.github.jan.supabase.storage.storage

class StorageSupabaseClient(private val supabaseClient: SupabaseClient) :
    SupabaseClient by supabaseClient {


    suspend fun insertPhotoStorage(
        userId: String,
        byteArray: ByteArray
    ): DataOrException<Boolean, Exception, Boolean> {
        return try {
            val bucket = supabaseClient.supabase.storage.from("profiles")
            bucket.upload("${userId}.jpg", byteArray, upsert = false)
            DataOrException(data = true, exception = null, isLoading = false)
        } catch (e: Exception) {
            DataOrException(data = null, exception = e, isLoading = false)
        }
    }

    suspend fun downloadPhotoStorage(userId: String): DataOrException<String, Exception, Boolean> {
       return try {
           val bucket = supabaseClient.supabase.storage.from("profiles")
           val url = bucket.publicUrl(path = "${userId}.jpg")
           DataOrException(data = url, exception = null, isLoading = false)
       } catch (e: Exception) {
          DataOrException(data = null, exception = e, isLoading = false)
       }
    }


}
