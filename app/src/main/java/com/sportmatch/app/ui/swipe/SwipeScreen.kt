package com.sportmatch.app.ui.swipe

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.components.MatchDialog
import com.sportmatch.app.ui.theme.*
import com.sportmatch.app.ui.viewmodel.SwipeViewModel

/**
 * Premium Swipe Screen
 * Elegant Tinder-style interface with minimal buttons
 */
@Composable
fun SwipeScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToChatList: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onMatchFound: (String) -> Unit,
    viewModel: SwipeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val deck by viewModel.deck.collectAsState()
    var showMatchDialog by remember { mutableStateOf(false) }
    var matchedUserName by remember { mutableStateOf("") }
    var matchId by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadCurrentUser()
    }

    LaunchedEffect(uiState) {
        if (uiState is com.sportmatch.app.ui.viewmodel.SwipeUiState.MatchFound) {
            val matchState = uiState as com.sportmatch.app.ui.viewmodel.SwipeUiState.MatchFound
            matchedUserName = matchState.user.name
            matchId = matchState.matchId
            showMatchDialog = true
            viewModel.resetMatchState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.md)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Logo/Title
            Text(
                text = "SportMatch",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Action Icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                IconButton(onClick = onNavigateToChatList) {
                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = "Chats",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = onNavigateToProfile) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = onNavigateToSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        // Main Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is com.sportmatch.app.ui.viewmodel.SwipeUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                is com.sportmatch.app.ui.viewmodel.SwipeUiState.Empty -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(Spacing.xl)
                    ) {
                        Text(
                            text = "No more profiles",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(Spacing.sm))
                        Text(
                            text = "Check back later for new matches",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                is com.sportmatch.app.ui.viewmodel.SwipeUiState.Success -> {
                    if (deck.isNotEmpty()) {
                        SwipeCardStack(
                            users = deck,
                            onSwipe = { user, type ->
                                viewModel.swipeUser(user, type)
                            }
                        )
                    }
                }
                is com.sportmatch.app.ui.viewmodel.SwipeUiState.Error -> {
                    Text(
                        text = (uiState as com.sportmatch.app.ui.viewmodel.SwipeUiState.Error).message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {}
            }
        }
    }

    // Match Dialog
    if (showMatchDialog) {
        MatchDialog(
            matchedUserName = matchedUserName,
            onSendMessage = {
                showMatchDialog = false
                onMatchFound(matchId)
            },
            onKeepSwiping = {
                showMatchDialog = false
            },
            onDismiss = {
                showMatchDialog = false
            }
        )
    }
}