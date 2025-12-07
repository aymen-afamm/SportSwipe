package com.sportmatch.app.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.sportmatch.app.data.model.MatchModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseMatchDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun createMatch(userId1: String, userId2: String): Result<String> {
        return try {
            val matchId = if (userId1 < userId2) {
                "${userId1}_${userId2}"
            } else {
                "${userId2}_${userId1}"
            }

            val match = MatchModel(
                matchId = matchId,
                users = listOf(userId1, userId2),
                createdAt = java.util.Date()
            )

            firestore.collection("matches")
                .document(matchId)
                .set(match.toMap())
                .await()

            Result.success(matchId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun checkAndCreateMatch(userId1: String, userId2: String, swipeType: com.sportmatch.app.data.model.SwipeModel.SwipeType): Result<String?> {
        return try {
            if (swipeType == com.sportmatch.app.data.model.SwipeModel.SwipeType.PASS) {
                return Result.success(null)
            }

            val otherUserSwipeDoc = firestore.collection("swipes")
                .document(userId2)
                .collection("swiped")
                .document(userId1)
                .get()
                .await()

            if (!otherUserSwipeDoc.exists()) {
                return Result.success(null)
            }

            val otherSwipeType = otherUserSwipeDoc.data?.get("type") as? String
            val otherUserLiked = otherSwipeType == com.sportmatch.app.data.model.SwipeModel.SwipeType.LIKE.name ||
                    otherSwipeType == com.sportmatch.app.data.model.SwipeModel.SwipeType.SUPER_LIKE.name

            if (otherUserLiked) {
                val matchId = if (userId1 < userId2) {
                    "${userId1}_${userId2}"
                } else {
                    "${userId2}_${userId1}"
                }

                val matchExists = firestore.collection("matches")
                    .document(matchId)
                    .get()
                    .await()
                    .exists()

                if (!matchExists) {
                    val match = MatchModel(
                        matchId = matchId,
                        users = listOf(userId1, userId2),
                        createdAt = java.util.Date()
                    )

                    firestore.collection("matches")
                        .document(matchId)
                        .set(match.toMap())
                        .await()

                    return Result.success(matchId)
                }
            }

            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMatches(userId: String): Result<List<MatchModel>> {
        return try {
            val snapshot = firestore.collection("matches")
                .whereArrayContains("users", userId)
                .get()
                .await()

            val matches = snapshot.documents.mapNotNull { MatchModel.fromDocument(it) }
            Result.success(matches)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun observeMatches(userId: String): Flow<List<MatchModel>> = callbackFlow {
        var listener: ListenerRegistration? = null
        try {
            listener = firestore.collection("matches")
                .whereArrayContains("users", userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val matches = snapshot?.documents?.mapNotNull { MatchModel.fromDocument(it) } ?: emptyList()
                    trySend(matches)
                }
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { listener?.remove() }
    }

    suspend fun getMatch(matchId: String): Result<MatchModel?> {
        return try {
            val document = firestore.collection("matches")
                .document(matchId)
                .get()
                .await()
            val match = MatchModel.fromDocument(document)
            Result.success(match)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

