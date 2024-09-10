package com.example.yourinterest.di

import com.example.yourinterest.data.client.AuthSapabaseClient
import com.example.yourinterest.data.client.DataBaseSapabaseClient
import com.example.yourinterest.data.client.KtorApi
import com.example.yourinterest.data.client.KtorApiImplementation
import com.example.yourinterest.data.client.PlacesNearbyClient
import com.example.yourinterest.data.client.RecoveryLocationClient
import com.example.yourinterest.data.client.SearchPlacesByQueryClient
import com.example.yourinterest.data.client.StorageSupabaseClient
import com.example.yourinterest.data.client.SupabaseClient
import com.example.yourinterest.data.client.SupabaseImplementation
import com.example.yourinterest.data.local.UsersDBLocalSource
import com.example.yourinterest.data.repository.PlacesNearbyRepository
import com.example.yourinterest.data.repository.RecoveryLocationRepository
import com.example.yourinterest.data.repository.SearchPlacesByQueryRepository
import com.example.yourinterest.data.repository.UserRepository
import com.example.yourinterest.db.InterestsDB
import com.example.yourinterest.viewmodel.AuthSapabaseViewModel
import com.example.yourinterest.viewmodel.PlacesNearbyViewModel
import com.example.yourinterest.viewmodel.RecoveryLocationViewModel
import com.example.yourinterest.viewmodel.SearchPlacesByQueryViewModel
import com.example.yourinterest.viewmodel.UserSapabaseViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        viewModelModule,
        repositoryModule,
        driverSQLModule,
        coreDataBase,
        localModule
    )
}

private  val clientModule = module {
    single { RecoveryLocationClient() }
    single { PlacesNearbyClient(get()) }
    single { SearchPlacesByQueryClient(get()) }
    single { AuthSapabaseClient(get()) }
    single { StorageSupabaseClient(get()) }
    single { DataBaseSapabaseClient(get()) }
    factory<KtorApi> { KtorApiImplementation() }
    factory<SupabaseClient> { SupabaseImplementation() }
}


private val coreDataBase = module {
    single { InterestsDB(get()) }
}

private val localModule = module {
    single { UsersDBLocalSource(get()) }
}

private  val viewModelModule  = module {
     single { RecoveryLocationViewModel() }
     single { PlacesNearbyViewModel() }
     single { SearchPlacesByQueryViewModel() }
     single { AuthSapabaseViewModel() }
     single { UserSapabaseViewModel() }

}

private  val repositoryModule = module {
    single { RecoveryLocationRepository() }
    single { PlacesNearbyRepository() }
    single { SearchPlacesByQueryRepository() }
    single { UserRepository() }

}




//para ios
fun initKoin() = initKoin {

}