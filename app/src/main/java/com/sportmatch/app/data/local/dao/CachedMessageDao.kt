package com.sportmatch.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sportmatch.app.data.local.entity.CachedMessageEntity

@Dao
interface CachedMessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<CachedMessageEntity>)

    @Query("SELECT * FROM cached_messages WHERE matchId = :matchId ORDER BY timestamp ASC")
    suspend fun getMessages(matchId: String): List<CachedMessageEntity>

    @Query("DELETE FROM cached_messages WHERE matchId = :matchId")
    suspend fun deleteMessages(matchId: String)

    @Query("DELETE FROM cached_messages")
    suspend fun clearAll()
}

