package com.learning.dogify.usecase

import com.learning.dogify.model.Breed

class FetchBreedsUseCase {
   suspend operator fun invoke():List<Breed> = listOf(Breed("Test get", ""), )
}