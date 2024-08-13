package com.example.yourinterest.data.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorApiImplementation() : KtorApi {

    private val apiURl = "https://api.foursquare.com"


    override val client = HttpClient {
        defaultRequest {
            url(apiURl)
            url {
                protocol = URLProtocol.HTTPS
            }
            headers {
                append("Authorization", "fsq3jysPCZjatMEn6A70xuWH08gnXTk8KZ4xH087DxCF+Gs=")
            }
        }
        //https://ktor.io/docs/client-basic-auth.html auth no ktor

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }
    }


}