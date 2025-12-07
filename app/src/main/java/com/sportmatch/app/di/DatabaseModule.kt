package com.sportmatch.app.di

import android.content.Context
import androidx.room.Room
import com.sportmatch.app.data.local.SportMatchDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): SportMatchDatabase {
        return Room.databaseBuilder(
            context,
            SportMatchDatabase::class.java,
            "sportmatch_database"
        ).build()
    }

    @Provides
    fun provideCachedUserDao(database: SportMatchDatabase) = database.cachedUserDao()

    @Provides
    fun provideCachedMessageDao(database: SportMatchDatabase) = database.cachedMessageDao()
}

