package com.silkfinik.vinylcatalog.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silkfinik.vinylcatalog.data.local.dao.VinylRecordDao
import com.silkfinik.vinylcatalog.data.local.entity.VinylRecordEntity

@Database(entities = [VinylRecordEntity::class], version = 1, exportSchema = false)
abstract class VinylDatabase : RoomDatabase() {
    abstract val vinylRecordDao: VinylRecordDao
}
