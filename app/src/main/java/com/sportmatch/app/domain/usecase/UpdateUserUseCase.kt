package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String, updates: Map<String, Any?>): Result<Unit> {
        return userRepository.updateUser(uid, updates)
    }
}

