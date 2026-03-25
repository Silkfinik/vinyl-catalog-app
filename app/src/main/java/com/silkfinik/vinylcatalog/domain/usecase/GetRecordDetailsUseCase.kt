package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import javax.inject.Inject

class GetRecordDetailsUseCase @Inject constructor(private val repository: VinylRepository) {
    suspend operator fun invoke(id: String): VinylRecord? = repository.getRecordById(id)
}
