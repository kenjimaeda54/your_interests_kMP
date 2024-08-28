package com.example.yourinterest.data.client

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth


class SupabaseImplementation() : SupabaseClient {

    override var supabase = createSupabaseClient(
        supabaseUrl = "https://xupsqneufzoezuxydbks.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inh1cHNxbmV1ZnpvZXp1eHlkYmtzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjQ2ODMzMTAsImV4cCI6MjA0MDI1OTMxMH0.stbmUPqDuqJaIXTmkN2hJotKiaLgqo-OxlX4tzCTSVc"
    ) {
        install(Auth)
    }
}