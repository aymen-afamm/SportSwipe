package com.sportmatch.app.ui.swipe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.ui.components.SwipeCard
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.Secondary
import com.sportmatch.app.ui.theme.Error
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.SwipeViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * SwipeScreen - Main discovery/swiping interface.
 * Features Tinder-style card stack with gesture-based interactions (like/pass/super-like).
 *
 * @param onNavigateToMatch Callback when users match
 * @param onNavigateToProfile Callback to navigate to profile
 * @param onNavigateToMatches Callback to navigate to matches list
 * @param onNavigateToSettings Callback to navigate to settings
 * @param viewModel SwipeViewModel for managing swipe state and logic
 */
@Composable
fun SwipeScreen(
    onNavigateToMatch: (String) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToMatches: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: SwipeViewModel = hiltViewModel()
) {
    val cardOffset = remember { Animatable(0f) }
    val cardRotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    var currentUserIndex by remember { mutableStateOf(0) }

    // Mock user data - replace with ViewModel state
    val mockUsers = listOf(
        UserModel(id = "1", name = "Alex", age = 28, photos = emptyList(), bio = "Basketball lover", sports = listOf("Basketball")),
        UserModel(id = "2", name = "Jordan", age = 26, photos = emptyList(), bio = "Gym enthusiast", sports = listOf("Gym")),
        UserModel(id = "3", name = "Casey", age = 29, photos = emptyList(), bio = "Runner", sports = listOf("Running"))
    )

    if (currentUserIndex >= mockUsers.size) {
        // No more profiles screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(Dimens.large)
            ) {
                Text(
                    text = "No More Profiles",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Check back later for more sports partners",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = Dimens.large)
                )
            }
        }
        return
    }

    val currentUser = mockUsers[currentUserIndex]

    val draggableState = rememberDraggableState(onDelta = { delta ->
        scope.launch {
            cardOffset.snapTo(cardOffset.value + delta)
            // Calculate rotation based on drag
            val rotation = (cardOffset.value / 100).coerceIn(-25f, 25f)
            cardRotation.snapTo(rotation)
        }
    })

    LaunchedEffect(currentUserIndex) {
        cardOffset.snapTo(0f)
        cardRotation.snapTo(0f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.large),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateToProfile) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Profile",
                    tint = Primary
                )
            }
            Text(
                text = "Discover",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onNavigateToMatches) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Matches",
                    tint = Primary
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // Card Stack
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.swipe_card_height)
                .padding(horizontal = Dimens.large),
            contentAlignment = Alignment.Center
        ) {
            SwipeCard(
                user = currentUser,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(cardOffset.value.roundToInt(), 0) }
                    .rotate(cardRotation.value)
                    .draggable(
                        state = draggableState,
                        orientation = Orientation.Horizontal
                    )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.large),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pass Button
            IconButton(
                onClick = {
                    scope.launch {
                        cardOffset.animateTo(-500f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                        currentUserIndex++
                    }
                },
                modifier = Modifier.size(Dimens.icon_size_extra_large)
            ) {
                Icon(
                    imageVector = Icons.Filled.ThumbDown,
                    contentDescription = "Pass",
                    tint = Error,
                    modifier = Modifier.size(Dimens.icon_size_large)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Like Button
            IconButton(
                onClick = {
                    scope.launch {
                        cardOffset.animateTo(500f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                        currentUserIndex++
                    }
                },
                modifier = Modifier.size(Dimens.icon_size_extra_large)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Like",
                    tint = Primary,
                    modifier = Modifier.size(Dimens.icon_size_large)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Super Like Button
            IconButton(
                onClick = {
                    scope.launch {
                        cardOffset.animateTo(500f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                        currentUserIndex++
                    }
                },
                modifier = Modifier.size(Dimens.icon_size_extra_large)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Super Like",
                    tint = Secondary,
                    modifier = Modifier.size(Dimens.icon_size_large)
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.large))
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeScreenPreview() {
    SportMatchTheme {
        SwipeScreen(
            onNavigateToMatch = { },
            onNavigateToProfile = { },
            onNavigateToMatches = { },
            onNavigateToSettings = { }
        )
    }
}

