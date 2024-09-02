package com.example.yourinterest.data.repository

import com.example.yourinterest.data.client.DataBaseSapabaseClient
import com.example.yourinterest.data.model.user.UserEntityResponse
import com.example.yourinterest.data.model.user.UserModel
import com.example.yourinterest.data.model.user.toUserModel
import com.example.yourinterest.util.DataOrException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository: KoinComponent {
    private val clientSupabase: DataBaseSapabaseClient by inject()


    suspend fun fetchDataBaseUser(phone: String): DataOrException<UserModel, Exception, Boolean> {
        val result = clientSupabase.fetchUsers(phone)
         if (result.data != null) {
            val userModel = result.data.toUserModel()
            return DataOrException(data = userModel, exception = null, isLoading = false)
         }
         return DataOrException(data = null, exception = result.exception, isLoading = false)
    }


    suspend fun insertUser(user: UserEntityResponse): DataOrException<Boolean, Exception, Boolean> {
        return clientSupabase.insertUser(user)
    }
}