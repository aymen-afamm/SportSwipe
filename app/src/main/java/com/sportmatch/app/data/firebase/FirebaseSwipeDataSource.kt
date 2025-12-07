package com.sportmatch.app.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.sportmatch.app.data.model.SwipeModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseSwipeDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun saveSwipe(userId: String, targetUserId: String, swipeType: SwipeModel.SwipeType): Result<Unit> {
        return try {
            val swipe = SwipeModel(
                userId = userId,
                targetUserId = targetUserId,
                type = swipeType,
                timestamp = java.util.Date()
            )
            firestore.collection("swipes")
                .document(userId)
                .collection("swiped")
                .document(targetUserId)
                .set(swipe.toMap())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun checkIfLiked(userId: String, targetUserId: String): Result<Boolean> {
        return try {
            val document = firestore.collection("swipes")
                .document(targetUserId)
                .collection("swiped")
                .document(userId)
                .get()
                .await()

            if (!document.exists()) {
                return Result.success(false)
            }

            val data = document.data
            val type = data?.get("type") as? String
            val isLiked = type == SwipeModel.SwipeType.LIKE.name || type == SwipeModel.SwipeType.SUPER_LIKE.name
            Result.success(isLiked)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSwipedUserIds(userId: String): Result<List<String>> {
        return try {
            val snapshot = firestore.collection("swipes")
                .document(userId)
                .collection("swiped")
                .get()
                .await()
            val userIds = snapshot.documents.map { it.id }
            Result.success(userIds)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

