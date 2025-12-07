package com.sportmatch.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.*

/**
 * Premium Primary Button Component
 * Large rounded corners, gradient, smooth animations
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    variant: ButtonVariant = ButtonVariant.Primary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "button_scale"
    )

    val gradientColors = when (variant) {
        ButtonVariant.Primary -> listOf(Primary, PrimaryDark)
        ButtonVariant.Sport -> listOf(SportAccent, SportAccentDark)
        ButtonVariant.Purple -> listOf(PurpleAccent, PurpleAccentDark)
    }

    Box(
        modifier = modifier
            .height(Spacing.buttonHeight)
            .fillMaxWidth()
            .scale(scale)
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (enabled) gradientColors else listOf(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                ),
                shape = ButtonShape
            )
            .clickable(
                enabled = enabled && !loading,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = TextOnPrimary,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (enabled) TextOnPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

enum class ButtonVariant {
    Primary,
    Sport,
    Purple
}