package com.sportmatch.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.domain.usecase.GetCurrentUserUseCase
import com.sportmatch.app.domain.usecase.UpdateUserUseCase
import com.sportmatch.app.domain.usecase.UploadPhotoUseCase
import com.sportmatch.app.utils.AgeCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadPhotoUseCase: UploadPhotoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> = _user.asStateFlow()

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            getCurrentUserUseCase().fold(
                onSuccess = { userModel ->
                    _user.value = userModel
                    _uiState.value = ProfileUiState.Success(userModel)
                },
                onFailure = {
                    _uiState.value = ProfileUiState.Error(it.message ?: "Failed to load profile")
                }
            )
        }
    }

    fun updateProfile(
        name: String,
        bio: String,
        gender: String,
        interests: List<String>,
        experienceLevel: String,
        dateOfBirth: Date?,
        preferences: com.sportmatch.app.data.model.PreferencesModel?
    ) {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            val updates = mutableMapOf<String, Any?>(
                "name" to name,
                "bio" to bio,
                "gender" to gender,
                "interests" to interests,
                "experienceLevel" to experienceLevel
            )

            dateOfBirth?.let {
                updates["dateOfBirth"] = com.google.firebase.Timestamp(it)
                updates["age"] = AgeCalculator.calculateAge(it)
            }

            preferences?.let {
                updates["preferences"] = it.toMap()
            }

            updateUserUseCase(currentUser.uid, updates).fold(
                onSuccess = {
                    loadUser()
                },
                onFailure = {
                    _uiState.value = ProfileUiState.Error(it.message ?: "Failed to update profile")
                }
            )
        }
    }

    fun uploadPhoto(uri: android.net.Uri, photoIndex: Int) {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            uploadPhotoUseCase(uri, currentUser.uid, photoIndex).fold(
                onSuccess = { url ->
                    val currentPhotos = _user.value?.photos?.toMutableList() ?: mutableListOf()
                    if (photoIndex < currentPhotos.size) {
                        currentPhotos[photoIndex] = url
                    } else {
                        currentPhotos.add(url)
                    }

                    val updates = mapOf("photos" to currentPhotos)
                    updateUserUseCase(currentUser.uid, updates).fold(
                        onSuccess = {
                            loadUser()
                        },
                        onFailure = {
                            _uiState.value = ProfileUiState.Error(it.message ?: "Failed to update photos")
                        }
                    )
                },
                onFailure = {
                    _uiState.value = ProfileUiState.Error(it.message ?: "Failed to upload photo")
                }
            )
        }
    }

    fun deletePhoto(photoIndex: Int) {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            val currentPhotos = _user.value?.photos?.toMutableList() ?: return@launch
            if (photoIndex < currentPhotos.size) {
                currentPhotos.removeAt(photoIndex)
                val updates = mapOf("photos" to currentPhotos)
                updateUserUseCase(currentUser.uid, updates).fold(
                    onSuccess = {
                        loadUser()
                    },
                    onFailure = {
                        _uiState.value = ProfileUiState.Error(it.message ?: "Failed to delete photo")
                    }
                )
            }
        }
    }
}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: UserModel?) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
