package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.model.MatchModel
import com.sportmatch.app.data.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchesListUseCase @Inject constructor(
    private val matchRepository: MatchRepository
) {
    operator fun invoke(userId: String): Flow<List<MatchModel>> {
        return matchRepository.observeMatches(userId)
    }
}

