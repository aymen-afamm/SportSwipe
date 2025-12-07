package com.sportmatch.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.*
import com.sportmatch.app.utils.toFormattedTime

/**
 * Premium Chat Bubble Component
 * Elegant rounded bubbles with smooth animations
 */
@Composable
fun ChatBubble(
    text: String,
    isSent: Boolean,
    timestamp: Long,
    modifier: Modifier = Modifier,
    isAnimated: Boolean = true
) {
    val scale by animateFloatAsState(
        targetValue = if (isAnimated) 1f else 0.9f,
        animationSpec = tween(200),
        label = "bubble_scale"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.screenHorizontal, vertical = Spacing.xs)
            .scale(scale),
        horizontalArrangement = if (isSent) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = ChatBubbleShape.topStart.value,
                        topEnd = ChatBubbleShape.topEnd.value,
                        bottomStart = if (isSent) ChatBubbleShape.bottomStart.value else 4.dp,
                        bottomEnd = if (isSent) 4.dp else ChatBubbleShape.bottomEnd.value
                    )
                )
                .background(
                    if (isSent) {
                        Brush.horizontalGradient(
                            colors = listOf(Primary, PrimaryDark)
                        )
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
                .padding(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = if (isSent) {
                    TextOnPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = timestamp.toFormattedTime(),
                style = MaterialTheme.typography.labelSmall,
                color = if (isSent) {
                    TextOnPrimary.copy(alpha = 0.7f)
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                }
            )
        }
    }
}