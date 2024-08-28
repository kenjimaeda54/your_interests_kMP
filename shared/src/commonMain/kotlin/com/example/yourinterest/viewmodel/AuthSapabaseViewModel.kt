package com.example.yourinterest.viewmodel

import com.example.yourinterest.data.client.AuthSapabaseClient
import com.example.yourinterest.util.CoroutineViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthSapabaseViewModel: CoroutineViewModel(), KoinComponent {
    private val client: AuthSapabaseClient by inject()

    fun sendCodeOTP(phone: String) {
        scope.launch {
             client.sendCodeOTP(phone)
        }
    }


}