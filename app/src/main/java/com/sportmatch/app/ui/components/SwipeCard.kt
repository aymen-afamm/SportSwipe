package com.sportmatch.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sportmatch.app.data.model.UserModel
import com.sportmatch.app.ui.theme.*

/**
 * Premium Swipe Card Component
 * Elegant Tinder-style card with floating shadow and smooth animations
 */
@Composable
fun SwipeCard(
    user: UserModel,
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    rotation: Float = 0f,
    alpha: Float = 1f
) {
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(300),
        label = "card_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(640.dp)
            .scale(animatedScale)
            .clip(SwipeCardShape),
        shape = SwipeCardShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevation.lg,
            pressedElevation = Elevation.md
        )
    ) {
        Box {
            // Main Image
            if (user.photos.isNotEmpty()) {
                AsyncImage(
                    model = user.photos.first(),
                    contentDescription = user.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(SwipeCardShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            // Gradient Overlay (bottom)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f),
                                Color.Black.copy(alpha = 0.7f),
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
            )

            // Content Overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(Spacing.lg)
            ) {
                // Name and Age
                Text(
                    text = "${user.name}, ${user.age}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                // Bio
                if (user.bio.isNotEmpty()) {
                    Text(
                        text = user.bio,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.95f),
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                }

                // Sport Tags
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    user.interests.take(4).forEach { interest ->
                        SportTag(
                            sport = interest,
                            selected = true,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
            }

            // Distance Badge (top right)
            if (user.distance != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(Spacing.md)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = Spacing.md, vertical = Spacing.sm)
                ) {
                    Text(
                        text = "${user.distance}km away",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}