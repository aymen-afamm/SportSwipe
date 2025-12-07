package com.sportmatch.app.data.model

import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

data class MatchModel(
    val matchId: String = "",
    val users: List<String> = emptyList(),
    val createdAt: Date = Date()
) {
    companion object {
        fun fromDocument(document: DocumentSnapshot): MatchModel? {
            return try {
                val data = document.data ?: return null
                MatchModel(
                    matchId = document.id,
                    users = (data["users"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                    createdAt = (data["createdAt"] as? com.google.firebase.Timestamp)?.toDate() ?: Date()
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "users" to users,
            "createdAt" to com.google.firebase.Timestamp(createdAt)
        )
    }

    fun getOtherUserId(currentUserId: String): String? {
        return users.firstOrNull { it != currentUserId }
    }
}

