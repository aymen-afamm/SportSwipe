package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.repository.UserRepository
import com.sportmatch.app.data.model.UserModel
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: UserModel): Result<Unit> {
        return userRepository.createUser(user)
    }
}

