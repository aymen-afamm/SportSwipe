package com.sportmatch.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.PrimaryDark
import com.sportmatch.app.ui.theme.TextPrimaryDark
import com.sportmatch.app.ui.theme.SportMatchTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * ChatBubble Component - Message bubble for chat screens.
 * Features smooth animations and directional styling (sent/received).
 *
 * @param text Message text content
 * @param isSent Whether the message was sent (true) or received (false)
 * @param timestamp Message timestamp in milliseconds
 * @param modifier Modifier for sizing and positioning
 * @param isAnimated Whether to animate the bubble appearance
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
            .padding(horizontal = Dimens.screen_padding, vertical = Dimens.extra_small)
            .scale(scale),
        horizontalArrangement = if (isSent) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = Dimens.chat_bubble_max_width)
                .clip(
                    RoundedCornerShape(
                        topStart = Dimens.chat_bubble_corner_radius,
                        topEnd = Dimens.chat_bubble_corner_radius,
                        bottomStart = if (isSent) Dimens.chat_bubble_corner_radius else 4.dp,
                        bottomEnd = if (isSent) 4.dp else Dimens.chat_bubble_corner_radius
                    )
                )
                .then(
                    if (isSent) {
                        Modifier.background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Primary, PrimaryDark)
                            )
                        )
                    } else {
                        Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                    }
                )
                .padding(horizontal = Dimens.medium, vertical = Dimens.small)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                color = if (isSent) {
                    TextPrimaryDark
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            Spacer(modifier = Modifier.height(Dimens.extra_small))
            Text(
                text = formatTimestamp(timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = if (isSent) {
                    TextPrimaryDark.copy(alpha = 0.7f)
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                }
            )
        }
    }
}

/**
 * Helper function to format timestamp for display
 */
private fun formatTimestamp(timestamp: Long): String {
    return try {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        format.format(Date(timestamp))
    } catch (e: Exception) {
        ""
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubblePreview() {
    SportMatchTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.large)
        ) {
            ChatBubble(
                text = "Hey! Let's play basketball tomorrow?",
                isSent = false,
                timestamp = System.currentTimeMillis()
            )
            ChatBubble(
                text = "Sure! What time works for you?",
                isSent = true,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}
