package com.sportmatch.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_users")
data class CachedUserEntity(
    @PrimaryKey
    val uid: String,
    val name: String,
    val age: Int,
    val bio: String,
    val gender: String,
    val interests: String,
    val photos: String,
    val lat: Double,
    val lon: Double,
    val experienceLevel: String,
    val cachedAt: Long
)

