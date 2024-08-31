package com.example.yourinterest.data.client

import Your_Interesests.shared.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth


class SupabaseImplementation() : SupabaseClient {
    val supabaseKey = BuildConfig.SUPABASE_KEY
    override var supabase = createSupabaseClient(
        supabaseUrl = "https://xupsqneufzoezuxydbks.supabase.co",
        supabaseKey = supabaseKey
    ) {
        install(Auth)
    }
}