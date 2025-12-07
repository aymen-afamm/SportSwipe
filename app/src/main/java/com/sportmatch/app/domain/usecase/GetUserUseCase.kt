package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.repository.UserRepository
import com.sportmatch.app.data.model.UserModel
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uid: String): Result<UserModel?> {
        return userRepository.getUser(uid)
    }
}

