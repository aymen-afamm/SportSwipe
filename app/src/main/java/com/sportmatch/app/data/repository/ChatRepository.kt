package com.sportmatch.app.data.repository

import com.sportmatch.app.data.firebase.FirebaseChatDataSource
import com.sportmatch.app.data.firebase.FirebaseStorageDataSource
import com.sportmatch.app.data.model.MessageModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatDataSource: FirebaseChatDataSource,
    private val storageDataSource: FirebaseStorageDataSource
) {
    suspend fun sendMessage(matchId: String, message: MessageModel): Result<Unit> {
        return chatDataSource.sendMessage(matchId, message)
    }

    suspend fun getMessages(matchId: String, limit: Int = 50): Result<List<MessageModel>> {
        return chatDataSource.getMessages(matchId, limit)
    }

    fun observeMessages(matchId: String): Flow<List<MessageModel>> {
        return chatDataSource.observeMessages(matchId)
    }

    suspend fun markMessagesAsSeen(matchId: String, currentUserId: String): Result<Unit> {
        return chatDataSource.markMessagesAsSeen(matchId, currentUserId)
    }

    suspend fun getLastMessage(matchId: String): Result<MessageModel?> {
        return chatDataSource.getLastMessage(matchId)
    }

    suspend fun uploadChatImage(uri: android.net.Uri, matchId: String, messageId: String): Result<String> {
        return storageDataSource.uploadChatImage(uri, matchId, messageId)
    }
}

