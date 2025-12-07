package com.sportmatch.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sportmatch.app.data.model.MessageModel
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.data.repository.ChatRepository
import com.sportmatch.app.domain.usecase.GetMessagesLiveUseCase
import com.sportmatch.app.domain.usecase.GetUserUseCase
import com.sportmatch.app.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessagesLiveUseCase: GetMessagesLiveUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val chatRepository: ChatRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> = _messages.asStateFlow()

    private val _otherUser = MutableStateFlow<UserModel?>(null)
    val otherUser: StateFlow<UserModel?> = _otherUser.asStateFlow()

    fun loadChat(matchId: String) {
        viewModelScope.launch {
            _uiState.value = ChatUiState.Loading
            getMessagesLiveUseCase(matchId).collectLatest { messageList ->
                _messages.value = messageList
                _uiState.value = ChatUiState.Success

                val currentUser = auth.currentUser ?: return@collectLatest
                markMessagesAsSeen(matchId, currentUser.uid)
            }
        }
    }

    fun loadOtherUser(matchId: String) {
        viewModelScope.launch {
            val currentUser = auth.currentUser ?: return@launch
            val matchDoc = firestore.collection("matches")
                .document(matchId)
                .get()
                .await()

            val match = com.sportmatch.app.data.model.MatchModel.fromDocument(matchDoc)
            val otherUserId = match?.getOtherUserId(currentUser.uid) ?: return@launch
            getUserUseCase(otherUserId).fold(
                onSuccess = { user ->
                    _otherUser.value = user
                },
                onFailure = {}
            )
        }
    }

    fun sendMessage(matchId: String, text: String) {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            val message = MessageModel(
                senderId = currentUser.uid,
                text = text,
                timestamp = Date()
            )
            sendMessageUseCase(matchId, message).fold(
                onSuccess = {},
                onFailure = {
                    _uiState.value = ChatUiState.Error(it.message ?: "Failed to send message")
                }
            )
        }
    }

    fun sendImageMessage(matchId: String, imageUrl: String) {
        val currentUser = auth.currentUser ?: return
        viewModelScope.launch {
            val message = MessageModel(
                senderId = currentUser.uid,
                imageUrl = imageUrl,
                timestamp = Date()
            )
            sendMessageUseCase(matchId, message).fold(
                onSuccess = {},
                onFailure = {
                    _uiState.value = ChatUiState.Error(it.message ?: "Failed to send image")
                }
            )
        }
    }

    private fun markMessagesAsSeen(matchId: String, currentUserId: String) {
        viewModelScope.launch {
            chatRepository.markMessagesAsSeen(matchId, currentUserId)
        }
    }
}

sealed class ChatUiState {
    object Loading : ChatUiState()
    object Success : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}
