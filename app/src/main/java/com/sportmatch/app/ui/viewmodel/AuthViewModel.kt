package com.sportmatch.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sportmatch.app.domain.usecase.CreateUserUseCase
import com.sportmatch.app.domain.usecase.GetCurrentUserUseCase
import com.sportmatch.app.utils.AgeCalculator
import com.sportmatch.app.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val createUserUseCase: CreateUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signUp(email: String, password: String, name: String, dateOfBirth: Date) {
        if (!ValidationUtils.isValidEmail(email)) {
            _uiState.value = AuthUiState.Error("Invalid email")
            return
        }

        if (!ValidationUtils.isValidPassword(password)) {
            _uiState.value = AuthUiState.Error("Password must be at least 6 characters")
            return
        }

        if (!ValidationUtils.isValidName(name)) {
            _uiState.value = AuthUiState.Error("Name must be at least 2 characters")
            return
        }

        if (!AgeCalculator.isAdult(dateOfBirth)) {
            _uiState.value = AuthUiState.Error("You must be at least 18 years old")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user ?: throw Exception("User creation failed")

                val age = AgeCalculator.calculateAge(dateOfBirth)
                val userModel = com.sportmatch.app.data.model.UserModel(
                    uid = user.uid,
                    name = name,
                    age = age,
                    dateOfBirth = dateOfBirth,
                    createdAt = Date()
                )

                createUserUseCase(userModel).fold(
                    onSuccess = {
                        _uiState.value = AuthUiState.Success(user)
                    },
                    onFailure = {
                        _uiState.value = AuthUiState.Error(it.message ?: "Failed to create user")
                    }
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Sign up failed")
            }
        }
    }

    fun signIn(email: String, password: String) {
        if (!ValidationUtils.isValidEmail(email)) {
            _uiState.value = AuthUiState.Error("Invalid email")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                _uiState.value = AuthUiState.Success(result.user)
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Sign in failed")
            }
        }
    }

    fun signInWithGoogle(account: GoogleSignInAccount) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                val result = auth.signInWithCredential(credential).await()
                val user = result.user ?: throw Exception("Google sign in failed")

                getCurrentUserUseCase().fold(
                    onSuccess = { userModel ->
                        if (userModel == null) {
                            val newUserModel = com.sportmatch.app.data.model.UserModel(
                                uid = user.uid,
                                name = user.displayName ?: "",
                                createdAt = Date()
                            )
                            createUserUseCase(newUserModel).fold(
                                onSuccess = {
                                    _uiState.value = AuthUiState.Success(user)
                                },
                                onFailure = {
                                    _uiState.value = AuthUiState.Error(it.message ?: "Failed to create user")
                                }
                            )
                        } else {
                            _uiState.value = AuthUiState.Success(user)
                        }
                    },
                    onFailure = {
                        _uiState.value = AuthUiState.Error(it.message ?: "Failed to get user")
                    }
                )
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Google sign in failed")
            }
        }
    }

    fun logout() {
        auth.signOut()
        _uiState.value = AuthUiState.Idle
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: FirebaseUser?) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
