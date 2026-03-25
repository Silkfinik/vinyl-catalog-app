package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import javax.inject.Inject

class SaveRecordUseCase @Inject constructor(private val repository: VinylRepository) {
    suspend operator fun invoke(record: VinylRecord) = repository.insertOrUpdate(record)
}
