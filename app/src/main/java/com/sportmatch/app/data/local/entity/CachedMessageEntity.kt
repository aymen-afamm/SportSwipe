package com.sportmatch.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_messages")
data class CachedMessageEntity(
    @PrimaryKey
    val messageId: String,
    val matchId: String,
    val senderId: String,
    val text: String?,
    val imageUrl: String?,
    val timestamp: Long,
    val seen: Boolean
)

