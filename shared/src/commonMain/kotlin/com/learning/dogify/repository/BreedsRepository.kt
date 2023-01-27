package com.learning.dogify.repository

import com.learning.dogify.model.Breed
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

class BreedsRepository internal constructor(
    private val remoteDataSource: BreedsRemoteDataSource,
    private val localDataSource: BreedsLocalDataSource
) {

    val breeds = localDataSource.breeds

    suspend fun get() = with(localDataSource.selectAll()) {
        if (isNullOrEmpty())
            return@with fetch()
        else
            this
    }

    suspend fun fetch() = supervisorScope {
        remoteDataSource.getBreeds().map {
            async { Breed(name = it, imageUrl = remoteDataSource.getBreedImage(it)) }
        }.awaitAll()
            .also {
                localDataSource.clear()
                it.map {
                    async { localDataSource.insert(it) }
                }.awaitAll()
            }
    }

    suspend fun update(breed: Breed) = localDataSource.update(breed)
}