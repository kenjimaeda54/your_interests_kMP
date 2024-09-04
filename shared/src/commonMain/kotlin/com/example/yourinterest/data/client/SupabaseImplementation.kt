package com.example.yourinterest.data.client

import Your_Interesests.shared.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage


class SupabaseImplementation() : SupabaseClient {
    val supabaseKey = BuildConfig.SUPABASE_KEY
    override var supabase = createSupabaseClient(
        supabaseUrl = "https://xupsqneufzoezuxydbks.supabase.co",
        supabaseKey = supabaseKey
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}