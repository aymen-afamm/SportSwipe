package com.sportmatch.app.ui.drawables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.PrimaryDark
import com.sportmatch.app.ui.theme.Secondary
import com.sportmatch.app.ui.theme.Tertiary

/**
 * Compose Drawable Replacements - Replaces XML drawable resources.
 * Provides composable functions for shapes, gradients, and backgrounds used in the app.
 */

/**
 * Gradient Splash Background
 * Replaces: bg_gradient_splash.xml
 */
@Composable
fun GradientSplashBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(Primary, Tertiary, Secondary),
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
    )
}

/**
 * Gradient Button Background
 * Replaces: bg_gradient_button.xml
 */
@Composable
fun GradientButtonBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(Primary, PrimaryDark)
            )
        )
    )
}

/**
 * Gradient Onboarding Page 1
 * Replaces: bg_gradient_onboarding_1.xml
 */
@Composable
fun GradientOnboarding1Background(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(Primary, Tertiary)
            )
        )
    )
}

/**
 * Gradient Onboarding Page 2
 * Replaces: bg_gradient_onboarding_2.xml
 */
@Composable
fun GradientOnboarding2Background(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(Secondary, Primary)
            )
        )
    )
}

/**
 * Gradient Onboarding Page 3
 * Replaces: bg_gradient_onboarding_3.xml
 */
@Composable
fun GradientOnboarding3Background(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(Tertiary, Secondary)
            )
        )
    )
}

/**
 * Card Sport Background
 * Replaces: bg_card_sport.xml
 */
@Composable
fun CardSportBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            color = Color.White
        )
    )
}

// Migration Notes:
// All shape XML drawables (shape_*.xml) should be replaced with:
// - MaterialTheme.shapes.* (extraSmall, small, medium, large, extraLarge)
// - CornerSmall, CornerMedium, CornerCard, CornerLarge, CornerButton, CornerFull from theme/Shapes.kt
//
// All icon XML drawables (ic_*.xml) should be replaced with:
// - Material Icons from androidx.compose.material.icons
// - SportMatchIcons object from ui/icons/SportMatchIcons.kt
// - Custom ImageVector definitions for brand-specific icons
//
// Summary of drawable replacements:
// ✅ bg_gradient_splash.xml → GradientSplashBackground()
// ✅ bg_gradient_button.xml → GradientButtonBackground()
// ✅ bg_gradient_onboarding_*.xml → GradientOnboarding*Background()
// ✅ bg_card_sport.xml → CardSportBackground()
// ✅ shape_rounded_button.xml → CornerButton shape
// ✅ shape_input_field.xml → CornerMedium shape
// ✅ shape_photo_slot.xml → CornerCard shape
// ✅ shape_circle_decorative.xml → CircleShape
// ✅ All ic_*.xml → MaterialTheme icons + SportMatchIcons
// ✅ selector_button_state.xml → animateColorAsState + Modifier.clickable

