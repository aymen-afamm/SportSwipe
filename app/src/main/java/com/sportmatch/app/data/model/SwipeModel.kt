package com.sportmatch.app.data.model

import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

data class SwipeModel(
    val userId: String = "",
    val targetUserId: String = "",
    val type: SwipeType = SwipeType.PASS,
    val timestamp: Date = Date()
) {
    enum class SwipeType {
        LIKE, PASS, SUPER_LIKE
    }

    companion object {
        fun fromDocument(document: DocumentSnapshot): SwipeModel? {
            return try {
                val data = document.data ?: return null
                SwipeModel(
                    userId = document.reference.parent.parent?.id ?: "",
                    targetUserId = document.id,
                    type = SwipeType.valueOf(data["type"] as? String ?: "PASS"),
                    timestamp = (data["timestamp"] as? com.google.firebase.Timestamp)?.toDate() ?: Date()
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "type" to type.name,
            "timestamp" to com.google.firebase.Timestamp(timestamp)
        )
    }
}

