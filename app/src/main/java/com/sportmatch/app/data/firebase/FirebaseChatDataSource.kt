package com.sportmatch.app.data.firebase

import androidx.contentpager.content.Query
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.sportmatch.app.data.model.MessageModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseChatDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun sendMessage(matchId: String, message: MessageModel): Result<Unit> {
        return try {
            firestore.collection("matches")
                .document(matchId)
                .collection("messages")
                .add(message.toMap())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMessages(matchId: String, limit: Int = 50): Result<List<MessageModel>> {
        return try {
            val snapshot = firestore.collection("matches")
                .document(matchId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()

            val messages = snapshot.documents.mapNotNull { doc ->
                MessageModel.fromDocument(doc)
            }.reversed()
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun observeMessages(matchId: String): Flow<List<MessageModel>> = callbackFlow {
        var listener: ListenerRegistration? = null
        try {
            listener = firestore.collection("matches")
                .document(matchId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val messages = snapshot?.documents?.mapNotNull { doc ->
                        MessageModel.fromDocument(doc)
                    } ?: emptyList()
                    trySend(messages)
                }
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    suspend fun markMessagesAsSeen(matchId: String, currentUserId: String): Result<Unit> {
        return try {
            val snapshot = firestore.collection("matches")
                .document(matchId)
                .collection("messages")
                .whereEqualTo("senderId", currentUserId)
                .whereEqualTo("seen", false)
                .get()
                .await()

            val batch = firestore.batch()
            snapshot.documents.forEach { doc ->
                batch.update(doc.reference, "seen", true)
            }
            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLastMessage(matchId: String): Result<MessageModel?> {
        return try {
            val snapshot = firestore.collection("matches")
                .document(matchId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .await()

            val message = snapshot.documents.firstOrNull()?.let { MessageModel.fromDocument(it) }
            Result.success(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

