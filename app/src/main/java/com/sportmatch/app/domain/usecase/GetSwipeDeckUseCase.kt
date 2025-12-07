package com.sportmatch.app.domain.usecase

import com.sportmatch.app.data.model.LocationModel
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.data.repository.SwipeRepository
import com.sportmatch.app.data.repository.UserRepository
import javax.inject.Inject

class GetSwipeDeckUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val swipeRepository: SwipeRepository
) {
    suspend operator fun invoke(
        currentUserId: String,
        location: LocationModel,
        maxDistance: Int,
        genderPreference: String,
        interests: List<String>
    ): Result<List<UserModel>> {
        return try {
            val swipedResult = swipeRepository.getSwipedUserIds(currentUserId)
            val swipedIds = swipedResult.getOrElse { emptyList() }.toSet()

            val usersResult = userRepository.getUsersForSwipe(
                currentUserId,
                location,
                maxDistance,
                genderPreference,
                interests
            )

            usersResult.map { users ->
                users.filter { it.uid !in swipedIds }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

