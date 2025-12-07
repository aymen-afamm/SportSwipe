package com.sportmatch.app.ui.chat

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.ui.components.ChatBubble
import com.sportmatch.app.ui.components.ImageMessageBubble
import com.sportmatch.app.ui.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    matchId: String,
    onNavigateBack: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val otherUser by viewModel.otherUser.collectAsState()
    var messageText by remember { mutableStateOf("") }
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val listState = rememberLazyListState()

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.sendImageMessage(matchId, it.toString())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadChat(matchId)
        viewModel.loadOtherUser(matchId)
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(otherUser?.name ?: "Chat") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                state = listState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(messages) { message ->
                    if (message.senderId == currentUserId) {
                        if (message.imageUrl != null) {
                            ImageMessageBubble(
                                imageUrl = message.imageUrl,
                                isSent = true,
                                timestamp = message.timestamp.time
                            )
                        } else {
                            ChatBubble(
                                text = message.text ?: "",
                                isSent = true,
                                timestamp = message.timestamp.time
                            )
                        }
                    } else {
                        if (message.imageUrl != null) {
                            ImageMessageBubble(
                                imageUrl = message.imageUrl,
                                isSent = false,
                                timestamp = message.timestamp.time
                            )
                        } else {
                            ChatBubble(
                                text = message.text ?: "",
                                isSent = false,
                                timestamp = message.timestamp.time
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                IconButton(onClick = { imagePicker.launch("image/*") }) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Add Image"
                    )
                }
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...") },
                    singleLine = true
                )
                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            viewModel.sendMessage(matchId, messageText)
                            messageText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}
