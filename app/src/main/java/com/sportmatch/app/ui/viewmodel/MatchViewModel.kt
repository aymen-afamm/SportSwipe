package com.sportmatch.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.data.model.MatchModel
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.domain.usecase.GetMatchesListUseCase
import com.sportmatch.app.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getMatchesListUseCase: GetMatchesListUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MatchUiState>(MatchUiState.Loading)
    val uiState: StateFlow<MatchUiState> = _uiState.asStateFlow()

    private val _matches = MutableStateFlow<List<MatchModel>>(emptyList())
    val matches: StateFlow<List<MatchModel>> = _matches.asStateFlow()

    private val _matchesWithUsers = MutableStateFlow<List<MatchWithUser>>(emptyList())
    val matchesWithUsers: StateFlow<List<MatchWithUser>> = _matchesWithUsers.asStateFlow()

    fun loadMatches() {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            _uiState.value = MatchUiState.Loading
            getMatchesListUseCase(currentUser.uid).collectLatest { matchList ->
                _matches.value = matchList
                loadUsersForMatches(matchList, currentUser.uid)
            }
        }
    }

    private fun loadUsersForMatches(matches: List<MatchModel>, currentUserId: String) {
        viewModelScope.launch {
            val matchesWithUsersList = matches.map { match ->
                val otherUserId = match.getOtherUserId(currentUserId) ?: ""
                val userResult = getUserUseCase(otherUserId)
                val user = userResult.getOrNull()
                MatchWithUser(match, user)
            }
            _matchesWithUsers.value = matchesWithUsersList
            _uiState.value = MatchUiState.Success
        }
    }
}

data class MatchWithUser(
    val match: MatchModel,
    val user: UserModel?
)

sealed class MatchUiState {
    object Loading : MatchUiState()
    object Success : MatchUiState()
    data class Error(val message: String) : MatchUiState()
}
