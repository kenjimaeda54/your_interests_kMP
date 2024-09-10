package com.example.yourinterest.data.repository

import com.example.yourinterest.data.client.DataBaseSapabaseClient
import com.example.yourinterest.data.local.UsersDBLocalSource
import com.example.yourinterest.data.model.user.UserEntityResponse
import com.example.yourinterest.data.model.user.UserModel
import com.example.yourinterest.data.model.user.toUserModel
import com.example.yourinterest.util.DataOrException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepository : KoinComponent {
    private val clientSupabase: DataBaseSapabaseClient by inject()
    private val userLocalResource: UsersDBLocalSource by inject()


    suspend fun fetchUser(phone: String? = null): DataOrException<UserModel, Exception, Boolean> {
        val user = userLocalResource.getUser()
        if (phone != null && user == null) {
            val result = clientSupabase.fetchUser(phone)
            if (result.data != null) {
                val userModel = result.data.toUserModel()
                return DataOrException(data = userModel, exception = null, isLoading = false)
            }
            return DataOrException(data = null, exception = result.exception, isLoading = false)

        }
        return user
    }


    suspend fun insertUser(user: UserEntityResponse): DataOrException<Boolean, Exception, Boolean> {
        userLocalResource.insertUser(user.toUserModel())
        return clientSupabase.insertUser(user)
    }
}