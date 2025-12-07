package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.repository.UserRepository
import com.sportmatch.app.data.model.UserModel
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<UserModel?> {
        return userRepository.getCurrentUser()
    }
}

