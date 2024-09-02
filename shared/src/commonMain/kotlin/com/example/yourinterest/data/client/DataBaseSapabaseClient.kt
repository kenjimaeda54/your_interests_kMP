package com.example.yourinterest.data.client

import com.example.yourinterest.data.model.user.UserEntityResponse
import com.example.yourinterest.util.DataOrException
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.ktor.http.HttpStatusCode

class DataBaseSapabaseClient(private val supabaseClient: SupabaseClient): SupabaseClient by supabaseClient {


    suspend fun fetchUsers(phone: String): DataOrException<UserEntityResponse, Exception, Boolean> {
        return try {
            val result = supabaseClient.supabase.from("users").select(
            columns = Columns.ALL){
                filter {
                    eq("phone", phone)
                }
            }.decodeSingle<UserEntityResponse>()
            DataOrException(data = result, exception = null, isLoading = false)
        } catch (exception: Exception) {
            DataOrException(data = null, exception = exception, isLoading = false)
        }
    }

    suspend fun  insertUser(user: UserEntityResponse): DataOrException<Boolean, Exception, Boolean> {
        return try {
           supabaseClient.supabase.from("users").insert(user)
            DataOrException(data = true, exception = null, isLoading = false)
        } catch (exception: Exception) {
            DataOrException(data = false, exception = exception, isLoading = false)
        }
    }


}