package com.sportmatch.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Idle)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun deleteAccount() {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            _uiState.value = SettingsUiState.Loading
            try {
                userRepository.deleteUser(currentUser.uid).fold(
                    onSuccess = {
                        currentUser.delete().await()
                        _uiState.value = SettingsUiState.AccountDeleted
                    },
                    onFailure = {
                        _uiState.value = SettingsUiState.Error(it.message ?: "Failed to delete account")
                    }
                )
            } catch (e: Exception) {
                _uiState.value = SettingsUiState.Error(e.message ?: "Failed to delete account")
            }
        }
    }

    fun reportUser(userId: String, reason: String) {
        viewModelScope.launch {
            _uiState.value = SettingsUiState.Loading
            // Implement report functionality
            _uiState.value = SettingsUiState.Reported
        }
    }

    fun blockUser(userId: String) {
        viewModelScope.launch {
            _uiState.value = SettingsUiState.Loading
            // Implement block functionality
            _uiState.value = SettingsUiState.Blocked
        }
    }

    fun logout() {
        auth.signOut()
        _uiState.value = SettingsUiState.LoggedOut
    }

    fun resetState() {
        _uiState.value = SettingsUiState.Idle
    }
}

sealed class SettingsUiState {
    object Idle : SettingsUiState()
    object Loading : SettingsUiState()
    object LoggedOut : SettingsUiState()
    object AccountDeleted : SettingsUiState()
    object Reported : SettingsUiState()
    object Blocked : SettingsUiState()
    data class Error(val message: String) : SettingsUiState()
}
