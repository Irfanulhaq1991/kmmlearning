package com.learning.dogify.di

import com.learning.dogify.usecase.FetchBreedsUseCase
import com.learning.dogify.usecase.GetBreedsUseCase
import com.learning.dogify.usecase.ToggleFavouriteStateUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val usecaseModule = module {
    factory { GetBreedsUseCase() }
    factory { FetchBreedsUseCase() }
    factory { ToggleFavouriteStateUseCase() }
}
private val sharedModules = listOf(usecaseModule)
fun initKoin(appDeclaration: KoinAppDeclaration = {})
        = startKoin {
    appDeclaration()
    modules(sharedModules)
}