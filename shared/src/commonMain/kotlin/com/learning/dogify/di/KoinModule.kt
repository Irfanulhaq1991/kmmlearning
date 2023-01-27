package com.learning.dogify.di

import com.learning.dogify.api.BreedsApi
import com.learning.dogify.repository.BreedsRemoteDataSource
import com.learning.dogify.repository.BreedsRepository
import com.learning.dogify.usecase.FetchBreedsUseCase
import com.learning.dogify.usecase.GetBreedsUseCase
import com.learning.dogify.usecase.ToggleFavouriteStateUseCase
import com.learning.dogify.util.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val apiModule = module {
    factory { BreedsApi() }
}

private val repositoryModule = module {
    single { BreedsRepository(get()) }
    factory { BreedsRemoteDataSource(get(), get()) }
}

private val usecaseModule = module {
    factory { GetBreedsUseCase() }
    factory { FetchBreedsUseCase() }
    factory { ToggleFavouriteStateUseCase() }
}
private val sharedModules =
    listOf(utilityModule, apiModule, repositoryModule, usecaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(sharedModules)
}