package com.sportmatch.app.data.repository

import com.sportmatch.app.data.firebase.FirebaseSwipeDataSource
import com.sportmatch.app.data.model.SwipeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SwipeRepository @Inject constructor(
    private val swipeDataSource: FirebaseSwipeDataSource
) {
    suspend fun saveSwipe(userId: String, targetUserId: String, swipeType: SwipeModel.SwipeType): Result<Unit> {
        return swipeDataSource.saveSwipe(userId, targetUserId, swipeType)
    }

    suspend fun checkIfLiked(userId: String, targetUserId: String): Result<Boolean> {
        return swipeDataSource.checkIfLiked(userId, targetUserId)
    }

    suspend fun getSwipedUserIds(userId: String): Result<List<String>> {
        return swipeDataSource.getSwipedUserIds(userId)
    }
}

