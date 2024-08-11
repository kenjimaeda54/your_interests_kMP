package com.example.yourinteresests.android

import com.example.yourinterest.di.initKoin
import android.app.Application
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@MainApplication)
         }
    }


}