package com.example.yourinterest.viewmodel

import com.example.yourinterest.data.client.StorageSupabaseClient
import com.example.yourinterest.data.model.user.UserEntityResponse
import com.example.yourinterest.data.model.user.UserModel
import com.example.yourinterest.data.model.user.UserWithPhotoByTeArray
import com.example.yourinterest.data.model.user.toUserModel
import com.example.yourinterest.data.repository.UserRepository
import com.example.yourinterest.util.CoroutineViewModel
import com.example.yourinterest.util.DataOrException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

class UserSapabaseViewModel: CoroutineViewModel(), KoinComponent {
    private val repository : UserRepository by inject()
    private  val storageSupabaseClient: StorageSupabaseClient by inject()
    private val _insertIsSuccess = MutableStateFlow<DataOrException<Boolean, Exception, Boolean>>(
        DataOrException(data = null, exception = null, isLoading = false)
    )
    private  val _user = MutableStateFlow<DataOrException<UserModel, Exception, Boolean>>(
        DataOrException(data = null, exception = null, isLoading = false)
    )
    val user: StateFlow<DataOrException<UserModel, Exception, Boolean>> = _user
    val insertIsSuccess: StateFlow<DataOrException<Boolean, Exception, Boolean>> = _insertIsSuccess

    fun fetchUser(phone: String) {
        scope.launch {
            _user.value = DataOrException(isLoading = true)
            _user.value = repository.fetchDataBaseUser(phone)
        }
    }

    fun clearData() {
        _insertIsSuccess.value = DataOrException(isLoading = false)
        _user.value = DataOrException(isLoading = false)
    }

    fun insertUser(user: UserWithPhotoByTeArray) {
        scope.launch {
            _insertIsSuccess.value = DataOrException(isLoading = true)
            storageSupabaseClient.insertPhotoStorage(userId = user.phone, byteArray = user.photo)
            val resultURL = storageSupabaseClient.downloadPhotoStorage(userId = user.phone)
            if (resultURL.exception != null) {
               _insertIsSuccess.value = DataOrException(data = null, exception = resultURL.exception, isLoading = false)
                return@launch
            }
            val userEntity = UserEntityResponse(
                id =   (Random.nextInt() * 10000 + Random.nextInt() + Random.nextInt() * 3),
                name = user.name,
                photoUrl = resultURL.data!!,
                phone = user.phone
            )
            _user.value = DataOrException(data = userEntity.toUserModel(), exception = null, isLoading = false)
            _insertIsSuccess.value = repository.insertUser(user = userEntity)
        }
    }
}