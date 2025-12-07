package com.sportmatch.app.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.components.ProfileImage
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.CornerCard
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.ChatViewModel

data class MatchItem(
    val matchId: String,
    val name: String,
    val lastMessage: String,
    val timestamp: String,
    val photoUrl: String?
)

/**
 * ChatListScreen / MatchesListScreen - Shows all active matches/conversations.
 *
 * @param onNavigateToChat Callback with match ID when chat is selected
 * @param onBackClick Callback for back button
 * @param viewModel ChatViewModel for managing matches list
 */
@Composable
fun ChatListScreen(
    onNavigateToChat: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    // Mock data
    val matches = listOf(
        MatchItem(
            matchId = "1",
            name = "Alex",
            lastMessage = "Let's play basketball tomorrow!",
            timestamp = "2:45 PM",
            photoUrl = null
        ),
        MatchItem(
            matchId = "2",
            name = "Jordan",
            lastMessage = "Sure! What time?",
            timestamp = "Yesterday",
            photoUrl = null
        ),
        MatchItem(
            matchId = "3",
            name = "Casey",
            lastMessage = "You: Sounds good!",
            timestamp = "2 days ago",
            photoUrl = null
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Matches",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Box(modifier = Modifier.weight(1f))
        }

        if (matches.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(Dimens.large)
                ) {
                    Text(
                        text = "No Matches Yet",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Start swiping to find your next sports partner",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = Dimens.medium)
                    )
                }
            }
        } else {
            // Matches list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimens.small),
                contentPadding = Dimens.medium
            ) {
                items(matches) { match ->
                    MatchListItem(
                        match = match,
                        onClick = { onNavigateToChat(match.matchId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MatchListItem(
    match: MatchItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CornerCard
            )
            .clickable(onClick = onClick)
            .padding(Dimens.medium),
        horizontalArrangement = Arrangement.spacedBy(Dimens.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(
            imageUrl = match.photoUrl,
            size = Dimens.avatar_size_large
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.small)
        ) {
            Text(
                text = match.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = match.lastMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }

        Text(
            text = match.timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview() {
    SportMatchTheme {
        ChatListScreen(
            onNavigateToChat = { },
            onBackClick = { }
        )
    }
}

