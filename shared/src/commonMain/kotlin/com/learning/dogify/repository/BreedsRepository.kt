package com.learning.dogify.repository

import com.learning.dogify.model.Breed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

class BreedsRepository  internal constructor(private val remoteDataSource: BreedsRemoteDataSource) {
    suspend fun get() = fetch()

    suspend fun fetch() = supervisorScope{
        remoteDataSource.getBreeds().map {
            async { Breed(name = it, imageUrl = remoteDataSource.getBreedImage(it)) }
        }.awaitAll()
    }
}