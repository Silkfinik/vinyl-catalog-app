package com.silkfinik.vinylcatalog.di

import android.content.Context
import androidx.room.Room
import com.silkfinik.vinylcatalog.data.local.VinylDatabase
import com.silkfinik.vinylcatalog.data.local.dao.VinylRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VinylDatabase {
        return Room.databaseBuilder(
            context,
            VinylDatabase::class.java,
            "vinyl_records.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecordDao(db: VinylDatabase): VinylRecordDao {
        return db.vinylRecordDao
    }
}
