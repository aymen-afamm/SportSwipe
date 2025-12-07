package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.model.SwipeModel
import com.sportmatch.app.data.repository.MatchRepository
import javax.inject.Inject

class CreateMatchIfNeededUseCase @Inject constructor(
    private val matchRepository: MatchRepository
) {
    suspend operator fun invoke(
        userId1: String,
        userId2: String,
        swipeType: SwipeModel.SwipeType
    ): Result<String?> {
        return matchRepository.checkAndCreateMatch(userId1, userId2, swipeType)
    }
}

