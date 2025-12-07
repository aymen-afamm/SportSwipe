package com.sportmatch.app.data.model

import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

data class MessageModel(
    val messageId: String = "",
    val senderId: String = "",
    val text: String? = null,
    val imageUrl: String? = null,
    val timestamp: Date = Date(),
    val seen: Boolean = false
) {
    companion object {
        fun fromDocument(document: DocumentSnapshot): MessageModel? {
            return try {
                val data = document.data ?: return null
                MessageModel(
                    messageId = document.id,
                    senderId = data["senderId"] as? String ?: "",
                    text = data["text"] as? String,
                    imageUrl = data["imageUrl"] as? String,
                    timestamp = (data["timestamp"] as? com.google.firebase.Timestamp)?.toDate() ?: Date(),
                    seen = data["seen"] as? Boolean ?: false
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "senderId" to senderId,
            "text" to text,
            "imageUrl" to imageUrl,
            "timestamp" to com.google.firebase.Timestamp(timestamp),
            "seen" to seen
        )
    }
}

