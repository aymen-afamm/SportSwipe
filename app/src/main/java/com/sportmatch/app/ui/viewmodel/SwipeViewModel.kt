package com.sportmatch.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.data.model.LocationModel
import com.sportmatch.app.data.model.SwipeModel
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.domain.usecase.CreateMatchIfNeededUseCase
import com.sportmatch.app.domain.usecase.GetCurrentUserUseCase
import com.sportmatch.app.domain.usecase.GetSwipeDeckUseCase
import com.sportmatch.app.domain.usecase.SendSwipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SwipeViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getSwipeDeckUseCase: GetSwipeDeckUseCase,
    private val sendSwipeUseCase: SendSwipeUseCase,
    private val createMatchIfNeededUseCase: CreateMatchIfNeededUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SwipeUiState>(SwipeUiState.Loading)
    val uiState: StateFlow<SwipeUiState> = _uiState.asStateFlow()

    private val _deck = MutableStateFlow<List<UserModel>>(emptyList())
    val deck: StateFlow<List<UserModel>> = _deck.asStateFlow()

    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    user?.let { loadDeck(it) }
                },
                onFailure = {
                    _uiState.value = SwipeUiState.Error(it.message ?: "Failed to load user")
                }
            )
        }
    }

    fun loadDeck(user: UserModel) {
        viewModelScope.launch {
            _uiState.value = SwipeUiState.Loading
            val location = user.location ?: LocationModel()
            val maxDistance = user.preferences?.distanceMax ?: 50
            val genderPreference = user.preferences?.gender ?: "All"
            val interests = user.interests

            getSwipeDeckUseCase(
                user.uid,
                location,
                maxDistance,
                genderPreference,
                interests
            ).fold(
                onSuccess = { users ->
                    _deck.value = users
                    _uiState.value = if (users.isEmpty()) {
                        SwipeUiState.Empty
                    } else {
                        SwipeUiState.Success
                    }
                },
                onFailure = {
                    _uiState.value = SwipeUiState.Error(it.message ?: "Failed to load deck")
                }
            )
        }
    }

    fun swipeUser(targetUser: UserModel, swipeType: SwipeModel.SwipeType) {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            sendSwipeUseCase(currentUser.uid, targetUser.uid, swipeType).fold(
                onSuccess = {
                    if (swipeType == SwipeModel.SwipeType.LIKE || swipeType == SwipeModel.SwipeType.SUPER_LIKE) {
                        createMatchIfNeededUseCase(currentUser.uid, targetUser.uid, swipeType).fold(
                            onSuccess = { matchId ->
                                if (matchId != null) {
                                    _uiState.value = SwipeUiState.MatchFound(matchId, targetUser)
                                }
                            },
                            onFailure = {}
                        )
                    }
                    removeUserFromDeck(targetUser.uid)
                },
                onFailure = {
                    _uiState.value = SwipeUiState.Error(it.message ?: "Failed to swipe")
                }
            )
        }
    }

    private fun removeUserFromDeck(userId: String) {
        _deck.value = _deck.value.filter { it.uid != userId }
        if (_deck.value.isEmpty()) {
            _currentUser.value?.let { loadDeck(it) }
        }
    }

    fun resetMatchState() {
        if (_uiState.value is SwipeUiState.MatchFound) {
            _uiState.value = SwipeUiState.Success
        }
    }
}

sealed class SwipeUiState {
    object Loading : SwipeUiState()
    object Success : SwipeUiState()
    object Empty : SwipeUiState()
    data class MatchFound(val matchId: String, val user: UserModel) : SwipeUiState()
    data class Error(val message: String) : SwipeUiState()
}
