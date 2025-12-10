package com.sportmatch.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sportmatch.app.ui.theme.*

/**
 * Premium Match Dialog Component
 * Elegant confetti-style match celebration
 */
@Composable
fun MatchDialog(
    matchedUserName: String,
    onSendMessage: () -> Unit,
    onKeepSwiping: () -> Unit,
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "match_animation")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "heart_scale"
    )

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(Spacing.xl),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Animated Heart Icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .scale(scale)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Secondary, SecondaryDark)
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "❤️",
                        style = MaterialTheme.typography.displayLarge
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.xl))

                // Match Title
                Text(
                    text = "It's a Match!",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                // Matched User Name
                Text(
                    text = "You and $matchedUserName liked each other",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = Spacing.lg)
                )

                Spacer(modifier = Modifier.height(Spacing.xxxl))

                // Action Buttons
                PrimaryButton(
                    text = "Send Message",
                    onClick = onSendMessage,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.Primary
                )

                Spacer(modifier = Modifier.height(Spacing.md))

                PrimaryButton(
                    text = "Keep Swiping",
                    onClick = onKeepSwiping,
                    modifier = Modifier.fillMaxWidth(),
                    variant = ButtonVariant.Primary
                )
            }
        }
    }
}