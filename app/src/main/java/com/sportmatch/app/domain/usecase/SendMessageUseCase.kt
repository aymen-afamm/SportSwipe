package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.model.MessageModel
import com.sportmatch.app.data.repository.ChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(matchId: String, message: MessageModel): Result<Unit> {
        return chatRepository.sendMessage(matchId, message)
    }
}

