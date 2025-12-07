package com.sportmatch.app.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sportmatch.app.data.model.LocationModel
import com.sportmatch.app.data.model.UserModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    suspend fun createUser(user: UserModel): Result<Unit> {
        return try {
            val currentUser = auth.currentUser ?: return Result.failure(Exception("User not authenticated"))
            firestore.collection("users")
                .document(currentUser.uid)
                .set(user.toMap())
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(uid: String): Result<UserModel?> {
        return try {
            val document = firestore.collection("users")
                .document(uid)
                .get()
                .await()
            val user = UserModel.fromDocument(document)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCurrentUser(): Result<UserModel?> {
        return try {
            val currentUser = auth.currentUser ?: return Result.success(null)
            getUser(currentUser.uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(uid: String, updates: Map<String, Any?>): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(uid)
                .update(updates)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun observeUser(uid: String): Flow<UserModel?> = callbackFlow {
        val listener = firestore.collection("users")
            .document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val user = snapshot?.let { UserModel.fromDocument(it) }
                trySend(user)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getUsersForSwipe(
        currentUserId: String,
        location: LocationModel,
        maxDistance: Int,
        genderPreference: String,
        interests: List<String>
    ): Result<List<UserModel>> {
        return try {
            val usersRef = firestore.collection("users")
            var query: Query = usersRef.whereNotEqualTo("uid", currentUserId)

            if (genderPreference.isNotEmpty() && genderPreference != "All") {
                query = query.whereEqualTo("gender", genderPreference)
            }

            val snapshot = query.get().await()
            val allUsers = snapshot.documents.mapNotNull { UserModel.fromDocument(it) }

            val filteredUsers = allUsers.filter { user ->
                val matchesGender = genderPreference.isEmpty() || genderPreference == "All" || user.gender == genderPreference
                val hasCommonInterests = interests.isEmpty() || user.interests.any { it in interests }
                val withinDistance = calculateDistance(
                    location.lat,
                    location.lon,
                    user.location?.lat ?: 0.0,
                    user.location?.lon ?: 0.0
                ) <= maxDistance

                matchesGender && hasCommonInterests && withinDistance && user.uid != currentUserId
            }

            Result.success(filteredUsers)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }

    suspend fun deleteUser(uid: String): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(uid)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

