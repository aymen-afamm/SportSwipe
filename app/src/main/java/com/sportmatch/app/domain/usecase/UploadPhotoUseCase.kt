package com.sportmatch.app.domain.usecase

import android.net.Uri
import com.sportmatch.app.data.repository.UserRepository
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(uri: Uri, userId: String, photoIndex: Int): Result<String> {
        return userRepository.uploadProfilePhoto(uri, userId, photoIndex)
    }
}

