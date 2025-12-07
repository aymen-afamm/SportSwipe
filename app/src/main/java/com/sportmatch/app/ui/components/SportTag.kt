package com.sportmatch.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.SportAccent
import com.sportmatch.app.ui.theme.Spacing
import com.sportmatch.app.ui.theme.TagShape
import com.sportmatch.app.ui.theme.TextOnPrimary

/**
 * Premium Sport Tag Component
 * Elegant, rounded tag with subtle gradient
 */
@Composable
fun SportTag(
    sport: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val alpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0.9f,
        animationSpec = tween(200),
        label = "tag_alpha"
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (selected) {
                        listOf(SportAccent, SportAccent.copy(alpha = 0.8f))
                    } else {
                        listOf(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                        )
                    }
                ),
                shape = TagShape
            )
            .alpha(alpha)
            .padding(horizontal = Spacing.md, vertical = Spacing.sm),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sport,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) {
                TextOnPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}