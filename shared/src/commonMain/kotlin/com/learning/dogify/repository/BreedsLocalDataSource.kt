package com.learning.dogify.repository


import com.learning.dogify.db.DogifyDatabase
import com.learning.dogify.model.Breed
import com.learning.dogify.util.DispatcherProvider
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class BreedsLocalDataSource(
    database: DogifyDatabase,
    private val dispatcherProvider: DispatcherProvider

) {

    private val dao = database.breedsQueries

    val breeds = dao.selectAll()
        .asFlow()
        .mapToList()
        .map { breeds -> breeds.map { Breed(it.name, it.imageUrl, it.isFavourite ?: false) } }

    suspend fun selectAll() = withContext(dispatcherProvider.io) {
        dao.selectAll { name, imageUrl, isFavourite ->
            Breed(name, imageUrl, isFavourite ?: false)
        }.executeAsList()
    }

    suspend fun insert(breed: Breed) = withContext(dispatcherProvider.io) {
        dao.insert(breed.name, breed.imageUrl, breed.isFavorite)
    }

    suspend fun update(breed: Breed) = withContext(dispatcherProvider.io) {
        dao.update(breed.imageUrl,breed.isFavorite,breed.name)
    }

    suspend fun clear() = withContext(dispatcherProvider.io){
        dao.clear()
    }
}

fun <T:Any> Query<T>.toFlow(): Flow<T> = flow{


}