package com.example.yourinterest.viewmodel

import com.example.yourinterest.data.client.AuthSapabaseClient
import com.example.yourinterest.util.CoroutineViewModel
import com.example.yourinterest.util.DataOrException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthSapabaseViewModel: CoroutineViewModel(), KoinComponent {
    private val client: AuthSapabaseClient by inject()
    private val _successSendCodeOTP = MutableStateFlow<DataOrException<Boolean, Exception, Boolean>>(DataOrException(isLoading = false))
    val successSendCodeOTP = _successSendCodeOTP
    private  val _successVerifyCodeOTP = MutableStateFlow<DataOrException<Boolean, Exception, Boolean>>(DataOrException(isLoading = false))
    val successVerifyCodeOTP = _successVerifyCodeOTP

    fun clearData() {
        _successSendCodeOTP.value = DataOrException(isLoading = false)
        _successVerifyCodeOTP.value = DataOrException(isLoading = false)
    }

    fun sendCodeOTP(phone: String) {

        scope.launch {
            _successSendCodeOTP.value = DataOrException(isLoading = true)
             _successSendCodeOTP.value = client.sendCodeOTP(phone)

        }
    }

    fun verifyCodeOTP(phone: String, code: String) {
        scope.launch {
             _successVerifyCodeOTP.value = DataOrException(isLoading = true)
             _successVerifyCodeOTP.value = client.verifyCodeOTP(phone, code)
        }
    }

}