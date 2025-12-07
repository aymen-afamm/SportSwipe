package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.model.MessageModel
import com.sportmatch.app.data.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesLiveUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(matchId: String): Flow<List<MessageModel>> {
        return chatRepository.observeMessages(matchId)
    }
}

