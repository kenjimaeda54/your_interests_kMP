package com.example.yourinterest.data.client

import com.example.yourinterest.util.DataOrException
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.OTP
import kotlinx.serialization.builtins.serializer


class AuthSapabaseClient(private val supabaseClient: SupabaseClient): SupabaseClient by supabaseClient {


    suspend fun sendCodeOTP(userPhone: String): DataOrException<Boolean, Exception, Boolean> {
        //se tu colocar o mesmo nome por exemplo phone = phone pode dar erro porque a propriedade e val
        //por isso usei userPhone
      return try {
           supabaseClient.supabase.auth.signInWith(OTP) {
               phone = userPhone;
               createUser = true

           }
           DataOrException(data = true, exception = null, isLoading = false)
       } catch (e: Exception) {
           DataOrException(data = null, exception = e, isLoading = false)
       }

    }

    suspend fun  verifyCodeOTP(userPhone: String, code: String): DataOrException<Boolean, Exception, Boolean> {
        return try {
            supabaseClient.supabase.auth.verifyPhoneOtp(
                type = OtpType.Phone.SMS,
                phone = userPhone,
                token = code
            )
            DataOrException(data = true, exception = null, isLoading = false)
        } catch (e: Exception) {
            DataOrException(data = null, exception = e, isLoading = false)
        }
    }

}