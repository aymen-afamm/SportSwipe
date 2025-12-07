package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.model.SwipeModel
import com.sportmatch.app.data.repository.SwipeRepository
import javax.inject.Inject

class SendSwipeUseCase @Inject constructor(
    private val swipeRepository: SwipeRepository
) {
    suspend operator fun invoke(
        userId: String,
        targetUserId: String,
        swipeType: SwipeModel.SwipeType
    ): Result<Unit> {
        return swipeRepository.saveSwipe(userId, targetUserId, swipeType)
    }
}

