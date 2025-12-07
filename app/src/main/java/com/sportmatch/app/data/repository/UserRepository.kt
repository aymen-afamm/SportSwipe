package com.sportmatch.app.data.repository

import com.sportmatch.app.data.firebase.FirebaseStorageDataSource
import com.sportmatch.app.data.firebase.FirebaseUserDataSource
import com.sportmatch.app.data.model.LocationModel
import com.sportmatch.app.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDataSource: FirebaseUserDataSource,
    private val storageDataSource: FirebaseStorageDataSource
) {
    suspend fun createUser(user: UserModel): Result<Unit> {
        return userDataSource.createUser(user)
    }

    suspend fun getUser(uid: String): Result<UserModel?> {
        return userDataSource.getUser(uid)
    }

    suspend fun getCurrentUser(): Result<UserModel?> {
        return userDataSource.getCurrentUser()
    }

    suspend fun updateUser(uid: String, updates: Map<String, Any?>): Result<Unit> {
        return userDataSource.updateUser(uid, updates)
    }

    fun observeUser(uid: String): Flow<UserModel?> {
        return userDataSource.observeUser(uid)
    }

    suspend fun getUsersForSwipe(
        currentUserId: String,
        location: LocationModel,
        maxDistance: Int,
        genderPreference: String,
        interests: List<String>
    ): Result<List<UserModel>> {
        return userDataSource.getUsersForSwipe(
            currentUserId,
            location,
            maxDistance,
            genderPreference,
            interests
        )
    }

    suspend fun uploadProfilePhoto(uri: android.net.Uri, userId: String, photoIndex: Int): Result<String> {
        return storageDataSource.uploadProfilePhoto(uri, userId, photoIndex)
    }

    suspend fun deleteUser(uid: String): Result<Unit> {
        return userDataSource.deleteUser(uid)
    }
}

