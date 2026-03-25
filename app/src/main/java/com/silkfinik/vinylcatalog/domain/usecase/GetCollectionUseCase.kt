package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(private val repository: VinylRepository) {
    operator fun invoke(): Flow<List<VinylRecord>> = repository.getCollection()
}
