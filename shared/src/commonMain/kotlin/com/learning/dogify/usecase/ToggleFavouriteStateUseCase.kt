package com.learning.dogify.usecase

import com.learning.dogify.model.Breed
import com.learning.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ToggleFavouriteStateUseCase: KoinComponent {
    private val breedsRepository: BreedsRepository by inject()
    suspend operator fun invoke(breed: Breed) {
        breedsRepository.update(  breed.copy(isFavorite = !breed.isFavorite))
    }
}