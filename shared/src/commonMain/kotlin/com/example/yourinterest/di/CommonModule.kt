package com.example.yourinterest.di

import com.example.yourinterest.data.client.KtorApi
import com.example.yourinterest.data.client.KtorApiImplementation
import com.example.yourinterest.data.client.PlacesNearbyClient
import com.example.yourinterest.data.client.RecoveryLocationClient
import com.example.yourinterest.data.client.SearchPlacesByQueryClient
import com.example.yourinterest.data.repository.PlacesNearbyRepository
import com.example.yourinterest.data.repository.RecoveryLocationRepository
import com.example.yourinterest.data.repository.SearchPlacesByQueryRepository
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel
import com.example.yourinterest.viewmodel.RecoveryLocationViewModel
import com.example.yourinterest.viewmodel.SearchPlacesByViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        viewModelModule,
        repositoryModule,

    )
}




private  val clientModule = module {
    factory { RecoveryLocationClient() }
    factory { PlacesNearbyClient(get()) }
    factory { SearchPlacesByQueryClient(get()) }
    factory <KtorApi> { KtorApiImplementation()  }
}



private  val viewModelModule  = module {
     single { RecoveryLocationViewModel() }
     single { PlacesNearbyViewModel() }
    single { SearchPlacesByViewModel() }

}

private  val repositoryModule = module {
    single { RecoveryLocationRepository() }
    single { PlacesNearbyRepository() }
    single { SearchPlacesByQueryRepository() }
}




//para ios
fun initKoin() = initKoin {

}