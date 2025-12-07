package com.sportmatch.app.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.data.model.MessageModel
import com.sportmatch.app.ui.components.ChatBubble
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.ChatViewModel

/**
 * ChatScreen - Direct messaging interface for matches.
 * Features message list with bubbles and input field.
 *
 * @param matchId ID of the match/conversation
 * @param onBackClick Callback for back button
 * @param viewModel ChatViewModel for managing messages
 */
@Composable
fun ChatScreen(
    matchId: String,
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    var messageText by remember { mutableStateOf("") }

    // Mock messages
    val messages = listOf(
        MessageModel(
            id = "1",
            senderId = "other",
            senderName = "Alex",
            text = "Hey! Let's play basketball tomorrow?",
            timestamp = System.currentTimeMillis() - 3600000,
            imageUrl = null
        ),
        MessageModel(
            id = "2",
            senderId = "me",
            senderName = "You",
            text = "Sure! What time works for you?",
            timestamp = System.currentTimeMillis() - 3000000,
            imageUrl = null
        ),
        MessageModel(
            id = "3",
            senderId = "other",
            senderName = "Alex",
            text = "How about 3 PM at the court near downtown?",
            timestamp = System.currentTimeMillis() - 1800000,
            imageUrl = null
        )
    )

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(Dimens.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                Column {
                    Text(
                        text = "Alex",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Online",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.medium))

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimens.small),
            reverseLayout = false
        ) {
            items(messages) { message ->
                ChatBubble(
                    text = message.text,
                    isSent = message.senderId == "me",
                    timestamp = message.timestamp
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.medium))

        // Message Input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.medium),
            horizontalArrangement = Arrangement.spacedBy(Dimens.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                placeholder = { Text("Type a message...") },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(Dimens.medium)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            IconButton(
                onClick = {
                    if (messageText.isNotEmpty()) {
                        // TODO: Send message via viewModel
                        messageText = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Send",
                    tint = Primary
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.small))
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    SportMatchTheme {
        ChatScreen(
            matchId = "1",
            onBackClick = { }
        )
    }
}

