package com.sportmatch.app.data.repository

import com.sportmatch.app.data.firebase.FirebaseMatchDataSource
import com.sportmatch.app.data.model.MatchModel
import com.sportmatch.app.data.model.SwipeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchRepository @Inject constructor(
    private val matchDataSource: FirebaseMatchDataSource
) {
    suspend fun createMatch(userId1: String, userId2: String): Result<String> {
        return matchDataSource.createMatch(userId1, userId2)
    }

    suspend fun checkAndCreateMatch(
        userId1: String,
        userId2: String,
        swipeType: SwipeModel.SwipeType
    ): Result<String?> {
        return matchDataSource.checkAndCreateMatch(userId1, userId2, swipeType)
    }

    suspend fun getMatches(userId: String): Result<List<MatchModel>> {
        return matchDataSource.getMatches(userId)
    }

    fun observeMatches(userId: String): Flow<List<MatchModel>> {
        return matchDataSource.observeMatches(userId)
    }

    suspend fun getMatch(matchId: String): Result<MatchModel?> {
        return matchDataSource.getMatch(matchId)
    }
}

