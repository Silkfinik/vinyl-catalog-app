package com.silkfinik.vinylcatalog.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.silkfinik.vinylcatalog.data.local.entity.VinylRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VinylRecordDao {

    @Query("SELECT * FROM vinyl_records WHERE isInWishlist = 0")
    fun getCollection(): Flow<List<VinylRecordEntity>>

    @Query("SELECT * FROM vinyl_records WHERE isInWishlist = 1")
    fun getWishlist(): Flow<List<VinylRecordEntity>>

    @Query("SELECT * FROM vinyl_records WHERE id = :id LIMIT 1")
    suspend fun getRecordById(id: String): VinylRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: VinylRecordEntity)

    @Delete
    suspend fun delete(record: VinylRecordEntity)
}
