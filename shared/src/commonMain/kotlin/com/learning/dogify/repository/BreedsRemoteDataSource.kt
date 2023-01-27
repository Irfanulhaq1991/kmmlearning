package com.learning.dogify.repository

import com.learning.dogify.api.BreedsApi
import kotlinx.coroutines.withContext
import com.learning.dogify.util.DispatcherProvider

internal class BreedsRemoteDataSource(
    private val api: BreedsApi,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getBreeds() = withContext(dispatcherProvider.io){
        api.getBreeds().breeds
    }

    suspend fun getBreedImage(breed:String) = withContext(dispatcherProvider.io){
        api.getRandomBreedImageFor(breed).breedImageUrl
    }
}