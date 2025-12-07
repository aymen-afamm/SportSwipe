package com.sportmatch.app.ui.swipe

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sportmatch.app.data.model.SwipeModel
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.ui.components.SwipeCard
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun SwipeCardStack(
    users: List<UserModel>,
    onSwipe: (UserModel, SwipeModel.SwipeType) -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    if (currentIndex >= users.size) {
        return
    }

    val currentUser = users[currentIndex]
    val nextUser = users.getOrNull(currentIndex + 1)

    val rotation = (offsetX / 10f).coerceIn(-30f, 30f)
    val alpha = animateFloatAsState(
        targetValue = if (isDragging) 0.7f else 1f,
        animationSpec = tween(200)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        nextUser?.let {
            SwipeCard(
                user = it,
                modifier = Modifier
                    .offset(y = 8.dp)
                    .graphicsLayer { scaleX = 0.95f; scaleY = 0.95f }
            )
        }

        SwipeCard(
            user = currentUser,
            modifier = Modifier
                .offset {
                    IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
                }
                .graphicsLayer {
                    rotationZ = rotation
                    alpha = alpha.value
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            isDragging = false
                            val threshold = size.width * 0.3f
                            when {
                                abs(offsetX) > threshold -> {
                                    val swipeType = if (offsetX > 0) {
                                        SwipeModel.SwipeType.LIKE
                                    } else {
                                        SwipeModel.SwipeType.PASS
                                    }
                                    onSwipe(currentUser, swipeType)
                                    currentIndex++
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                                offsetY < -threshold -> {
                                    onSwipe(currentUser, SwipeModel.SwipeType.SUPER_LIKE)
                                    currentIndex++
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                                else -> {
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                            }
                        }
                    ) { change, dragAmount ->
                        isDragging = true
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            com.sportmatch.app.ui.components.PassButton(
                onClick = {
                    onSwipe(currentUser, SwipeModel.SwipeType.PASS)
                    currentIndex++
                }
            )
            com.sportmatch.app.ui.components.SuperLikeButton(
                onClick = {
                    onSwipe(currentUser, SwipeModel.SwipeType.SUPER_LIKE)
                    currentIndex++
                }
            )
            com.sportmatch.app.ui.components.LikeButton(
                onClick = {
                    onSwipe(currentUser, SwipeModel.SwipeType.LIKE)
                    currentIndex++
                }
            )
        }
    }
}
