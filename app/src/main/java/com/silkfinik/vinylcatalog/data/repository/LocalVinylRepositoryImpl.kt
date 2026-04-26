package com.silkfinik.vinylcatalog.data.repository

import com.silkfinik.vinylcatalog.data.local.dao.VinylRecordDao
import com.silkfinik.vinylcatalog.data.mapper.toDomain
import com.silkfinik.vinylcatalog.data.mapper.toEntity
import com.silkfinik.vinylcatalog.data.remote.DiscogsApi
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalVinylRepositoryImpl @Inject constructor(
    private val dao: VinylRecordDao,
    private val api: DiscogsApi
) : VinylRepository {

    override fun getCollection(): Flow<List<VinylRecord>> {
        return dao.getCollection().map { list -> list.map { it.toDomain() } }
    }

    override fun getWishlist(): Flow<List<VinylRecord>> {
        return dao.getWishlist().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getRecordById(id: String): VinylRecord? {
        return dao.getRecordById(id)?.toDomain()
    }

    override suspend fun insertOrUpdate(record: VinylRecord) {
        dao.insertOrUpdate(record.toEntity())
    }

    override suspend fun delete(record: VinylRecord) {
        dao.delete(record.toEntity())
    }

    override suspend fun searchDiscogs(query: String, genre: String?): Result<List<VinylRecord>> {
        return try {
            val response = api.searchReleases(query = query, genre = genre)
            Result.success(response.results.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
