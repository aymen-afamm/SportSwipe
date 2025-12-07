package com.sportmatch.app.data.firebase

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseStorageDataSource @Inject constructor(
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) {
    suspend fun uploadPhoto(uri: Uri, path: String): Result<String> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("User not authenticated"))
            val storageRef = storage.reference.child(path)
            val uploadTask = storageRef.putFile(uri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await()
            Result.success(downloadUrl.toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadProfilePhoto(uri: Uri, userId: String, photoIndex: Int): Result<String> {
        val path = "users/$userId/photos/photo_$photoIndex.jpg"
        return uploadPhoto(uri, path)
    }

    suspend fun uploadChatImage(uri: Uri, matchId: String, messageId: String): Result<String> {
        val path = "matches/$matchId/messages/$messageId.jpg"
        return uploadPhoto(uri, path)
    }

    suspend fun deletePhoto(url: String): Result<Unit> {
        return try {
            val storageRef = storage.getReferenceFromUrl(url)
            storageRef.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

