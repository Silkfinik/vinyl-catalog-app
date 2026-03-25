package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(private val repository: VinylRepository) {
    suspend operator fun invoke(record: VinylRecord) = repository.delete(record)
}
