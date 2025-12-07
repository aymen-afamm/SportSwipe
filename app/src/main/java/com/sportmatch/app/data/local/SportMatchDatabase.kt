package com.sportmatch.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sportmatch.app.data.local.dao.CachedMessageDao
import com.sportmatch.app.data.local.dao.CachedUserDao
import com.sportmatch.app.data.local.entity.CachedMessageEntity
import com.sportmatch.app.data.local.entity.CachedUserEntity

@Database(
    entities = [CachedUserEntity::class, CachedMessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SportMatchDatabase : RoomDatabase() {
    abstract fun cachedUserDao(): CachedUserDao
    abstract fun cachedMessageDao(): CachedMessageDao
}

