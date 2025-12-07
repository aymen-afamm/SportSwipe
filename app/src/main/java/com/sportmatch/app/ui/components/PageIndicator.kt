package com.sportmatch.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.DividerLight
import com.sportmatch.app.ui.theme.SportMatchTheme

/**
 * PageIndicator - Horizontal pager indicator dots.
 * Shows current page progress with animated active indicator.
 *
 * @param pageCount Total number of pages
 * @param currentPage Current page index (0-based)
 * @param modifier Modifier for positioning
 * @param indicatorSize Size of each dot
 * @param activeColor Color of active indicator
 * @param inactiveColor Color of inactive indicators
 * @param spacing Spacing between indicators
 */
@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = Dimens.indicator_dot_size,
    activeColor: Color = Primary,
    inactiveColor: Color = DividerLight,
    spacing: Dp = Dimens.indicator_spacing
) {
    Row(
        modifier = modifier
            .height(indicatorSize + Dimens.small)
            .padding(Dimens.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isActive = index == currentPage

            val width by animateDpAsState(
                targetValue = if (isActive) indicatorSize * 2 else indicatorSize,
                animationSpec = tween(300),
                label = "indicator_width"
            )

            val color by animateColorAsState(
                targetValue = if (isActive) activeColor else inactiveColor,
                animationSpec = tween(300),
                label = "indicator_color"
            )

            Box(
                modifier = Modifier
                    .width(width)
                    .height(indicatorSize)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(indicatorSize / 2)
                    )
            )

            if (index < pageCount - 1) {
                Box(modifier = Modifier.width(spacing))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageIndicatorPreview() {
    SportMatchTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.large)
        ) {
            PageIndicator(
                pageCount = 5,
                currentPage = 2
            )
        }
    }
}

