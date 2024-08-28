package com.example.yourinterest.data.client

import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import kotlinx.serialization.builtins.serializer


class AuthSapabaseClient(private val supabaseClient: SupabaseClient): SupabaseClient by supabaseClient {


    suspend fun sendCodeOTP(userPhone: String) {
        //se tu colocar o mesmo nome por exemplo phone = phone pode dar erro porque a propriedade e val
        //por isso usei userPhone
       supabaseClient.supabase.auth.signInWith(OTP) {
             phone = userPhone;
             createUser = true

        }

    }

}