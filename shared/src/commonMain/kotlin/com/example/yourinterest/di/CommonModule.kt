package com.example.yourinterest.di

import com.example.yourinterest.client.RecoveryLocationClient
import com.example.yourinterest.repository.RecoveryLocationRepository
import com.example.yourinterest.viewmodel.RecoveryLocation
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        viewModelModule,
        repositoryModule
    )
}


private  val clientModule = module {
    single { RecoveryLocationClient() }
}

private  val viewModelModule  = module {
     single { RecoveryLocation() }
}

private  val repositoryModule = module {
    single { RecoveryLocationRepository() }
}

fun initKoin() = initKoin {}