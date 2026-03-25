package com.silkfinik.vinylcatalog.domain.repository

import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import kotlinx.coroutines.flow.Flow

interface VinylRepository {
    fun getCollection(): Flow<List<VinylRecord>>
    fun getWishlist(): Flow<List<VinylRecord>>
    suspend fun getRecordById(id: String): VinylRecord?
    suspend fun insertOrUpdate(record: VinylRecord)
    suspend fun delete(record: VinylRecord)
    suspend fun searchDiscogs(query: String): Result<List<VinylRecord>>
}
