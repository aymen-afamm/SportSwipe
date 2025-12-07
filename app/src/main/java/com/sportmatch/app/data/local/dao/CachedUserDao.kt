package com.sportmatch.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sportmatch.app.data.local.entity.CachedUserEntity

@Dao
interface CachedUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<CachedUserEntity>)

    @Query("SELECT * FROM cached_users WHERE uid IN (:uids)")
    suspend fun getUsers(uids: List<String>): List<CachedUserEntity>

    @Query("DELETE FROM cached_users WHERE cachedAt < :timestamp")
    suspend fun deleteOldCache(timestamp: Long)

    @Query("DELETE FROM cached_users")
    suspend fun clearAll()
}

