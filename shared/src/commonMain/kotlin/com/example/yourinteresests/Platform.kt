package com.example.yourinteresests

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform