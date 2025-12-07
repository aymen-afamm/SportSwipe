package com.sportmatch.app.data.model

import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

data class UserModel(
    val uid: String = "",
    val name: String = "",
    val age: Int = 0,
    val dateOfBirth: Date? = null,
    val bio: String = "",
    val gender: String = "",
    val interests: List<String> = emptyList(),
    val photos: List<String> = emptyList(),
    val location: LocationModel? = null,
    val preferences: PreferencesModel? = null,
    val experienceLevel: String = "",
    val createdAt: Date? = null
) {
    companion object {
        fun fromDocument(document: DocumentSnapshot): UserModel? {
            return try {
                val data = document.data ?: return null
                UserModel(
                    uid = document.id,
                    name = data["name"] as? String ?: "",
                    age = (data["age"] as? Long)?.toInt() ?: 0,
                    dateOfBirth = (data["dateOfBirth"] as? com.google.firebase.Timestamp)?.toDate(),
                    bio = data["bio"] as? String ?: "",
                    gender = data["gender"] as? String ?: "",
                    interests = (data["interests"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                    photos = (data["photos"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                    location = (data["location"] as? Map<*, *>)?.let {
                        LocationModel(
                            lat = (it["lat"] as? Double) ?: 0.0,
                            lon = (it["lon"] as? Double) ?: 0.0
                        )
                    },
                    preferences = (data["preferences"] as? Map<*, *>)?.let {
                        PreferencesModel(
                            gender = it["gender"] as? String ?: "",
                            distanceMax = (it["distanceMax"] as? Long)?.toInt() ?: 50
                        )
                    },
                    experienceLevel = data["experienceLevel"] as? String ?: "",
                    createdAt = (data["createdAt"] as? com.google.firebase.Timestamp)?.toDate()
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "age" to age,
            "dateOfBirth" to dateOfBirth?.let { com.google.firebase.Timestamp(it) },
            "bio" to bio,
            "gender" to gender,
            "interests" to interests,
            "photos" to photos,
            "location" to location?.toMap(),
            "preferences" to preferences?.toMap(),
            "experienceLevel" to experienceLevel,
            "createdAt" to (createdAt?.let { com.google.firebase.Timestamp(it) } ?: com.google.firebase.Timestamp.now())
        )
    }
}

