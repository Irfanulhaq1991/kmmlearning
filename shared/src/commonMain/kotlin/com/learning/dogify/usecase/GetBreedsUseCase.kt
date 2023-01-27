package com.learning.dogify.usecase

import com.learning.dogify.model.Breed
import com.learning.dogify.repository.BreedsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetBreedsUseCase:KoinComponent{
    private val breedsRepository:BreedsRepository by inject()
    suspend operator fun invoke():List<Breed> =
        breedsRepository.get()

}
