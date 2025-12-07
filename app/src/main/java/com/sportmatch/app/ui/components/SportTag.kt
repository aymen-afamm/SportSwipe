package com.sportmatch.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.CornerFull
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.TextPrimaryDark
import com.sportmatch.app.ui.theme.SportMatchTheme

/**
 * SportMatch Tag Component - Pill-shaped tags for sport selection/display.
 * Features smooth selection animation and gradient background.
 *
 * @param sport Sport name to display
 * @param modifier Modifier for sizing and positioning
 * @param selected Whether the tag is currently selected
 * @param onClick Optional callback when tag is clicked
 */
@Composable
fun SportTag(
    sport: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val alpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0.7f,
        animationSpec = tween(200),
        label = "tag_alpha"
    )

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = if (selected) {
                        listOf(Primary, Primary.copy(alpha = 0.8f))
                    } else {
                        listOf(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                        )
                    }
                ),
                shape = CornerFull
            )
            .alpha(alpha)
            .clickable(
                enabled = onClick != null,
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick?.invoke() }
            )
            .padding(horizontal = Dimens.tag_padding_horizontal, vertical = Dimens.tag_padding_vertical),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sport,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) {
                TextPrimaryDark
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SportTagPreview() {
    SportMatchTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(Dimens.large)) {
            SportTag(sport = "Basketball", selected = true)
        }
    }
}
