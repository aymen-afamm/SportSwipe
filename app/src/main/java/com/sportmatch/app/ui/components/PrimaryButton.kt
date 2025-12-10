package com.sportmatch.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.CornerButton
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.PrimaryDark
import com.sportmatch.app.ui.theme.Secondary
import com.sportmatch.app.ui.theme.SecondaryDark
import com.sportmatch.app.ui.theme.Tertiary
import com.sportmatch.app.ui.theme.TertiaryDark
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.theme.TextPrimaryDark

enum class ButtonVariant {
    Primary, Secondary, Tertiary
}

/**
 * Premium Primary Button Component with gradient and animations.
 *
 * @param text Display text on the button
 * @param onClick Lambda invoked when button is clicked
 * @param modifier Modifier for sizing and positioning
 * @param enabled Whether the button is clickable
 * @param loading Whether to show loading indicator
 * @param variant Button style variant (Primary/Secondary/Tertiary)
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
        ButtonVariant.Secondary -> listOf(Secondary, SecondaryDark)
        ButtonVariant.Tertiary -> listOf(Tertiary, TertiaryDark)
    }

    Box(
        modifier = modifier
            .height(Dimens.button_height)
            .fillMaxWidth()
            .scale(scale)
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (enabled) gradientColors else listOf(
                        MaterialTheme.colorScheme.surfaceVariant,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                ),
                shape = CornerButton
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
                color = TextPrimaryDark,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (enabled) TextPrimaryDark else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    SportMatchTheme {
        Box(modifier = Modifier.fillMaxWidth().height(300.dp).background(MaterialTheme.colorScheme.background).then(Modifier.fillMaxWidth())) {
            PrimaryButton(
                text = "Login",
                onClick = { },
                modifier = Modifier.padding(Dimens.large)
            )
        }
    }
}