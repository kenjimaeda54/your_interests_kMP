package com.example.yourinterest.viewmodel

import com.example.yourinterest.data.model.user.UserEntityResponse
import com.example.yourinterest.data.model.user.UserModel
import com.example.yourinterest.data.repository.UserRepository
import com.example.yourinterest.util.CoroutineViewModel
import com.example.yourinterest.util.DataOrException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserSapabaseViewModel: CoroutineViewModel(), KoinComponent {
    private val repository : UserRepository by inject()
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

    fun insertUser(user: UserEntityResponse) {
        scope.launch {
            _insertIsSuccess.value = DataOrException(isLoading = true)
            _insertIsSuccess.value = repository.insertUser(user)
        }
    }
}