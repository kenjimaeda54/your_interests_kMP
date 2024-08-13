package com.example.yourinterest.data.client

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder

interface KtorApi {
    val client: HttpClient

}