package com.learning.dogify.usecase

import com.learning.dogify.model.Breed

class GetBreedsUseCase{
    suspend operator fun invoke():List<Breed> = listOf(Breed("Test get", ""), )

}
