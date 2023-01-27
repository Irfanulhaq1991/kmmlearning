package com.learning.dogify.api

import com.learning.dogify.api.model.BreedImageResponse
import com.learning.dogify.api.model.BreedsResponse
import io.ktor.client.request.*
import kotlin.collections.get

internal class BreedsApi:KtorApi() {
    suspend fun getBreeds(): BreedsResponse =
        client.get {
            apiUrl("breeds/list")
        }
    suspend fun getRandomBreedImageFor(breed: String):
            BreedImageResponse = client.get {
        apiUrl("breed/$breed/images/random")
    }
}