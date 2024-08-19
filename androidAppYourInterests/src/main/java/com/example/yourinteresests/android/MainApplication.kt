package com.example.yourinteresests.android

import com.example.yourinterest.di.initKoin
import android.app.Application
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MainApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@MainApplication)
         }
    }


}

val appModule = module {
    viewModel { PlacesNearbyViewModel() }
}