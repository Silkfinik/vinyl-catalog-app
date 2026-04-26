package com.silkfinik.vinylcatalog.domain.usecase

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import javax.inject.Inject

class SearchDiscogsUseCase @Inject constructor(private val repository: VinylRepository) {
    suspend operator fun invoke(query: String, genre: String? = null): Result<List<VinylRecord>> = repository.searchDiscogs(query, genre)
}
