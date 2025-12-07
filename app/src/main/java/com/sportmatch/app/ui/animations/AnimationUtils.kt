package com.sportmatch.app.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * Animation Utilities for SportMatch App
 * Provides reusable animation specs and composable functions.
 */

// Animation Specs
object AnimationSpecs {
    val FastAnimation = tween<Float>(300)
    val MediumAnimation = tween<Float>(600)
    val SlowAnimation = tween<Float>(1000)

    val SpringDamping = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val StiffSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMedium
    )
}

/**
 * Fade In/Out animation
 * Used for screen transitions
 */
@Composable
fun FadeInOutAnimation(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = AnimationSpecs.MediumAnimation),
        exit = fadeOut(animationSpec = AnimationSpecs.MediumAnimation),
        modifier = modifier
    ) {
        content()
    }
}

/**
 * Slide Up/Down animation
 * Used for bottom sheets, modals
 */
@Composable
fun SlideUpDownAnimation(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = AnimationSpecs.MediumAnimation
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = AnimationSpecs.MediumAnimation
        ),
        modifier = modifier
    ) {
        content()
    }
}

/**
 * Scale animation helper
 * Used for button presses, card interactions
 */
@Composable
fun rememberScaleAnimatable(initialValue: Float = 1f): Animatable<Float, *> {
    return remember { Animatable(initialValue) }
}

/**
 * Swipe card animation
 * Animates card exit with rotation and offset
 *
 * @param offsetX Horizontal offset
 * @param rotation Rotation angle
 * @param onAnimationEnd Callback when animation completes
 */
suspend fun animateSwipeCard(
    offsetAnimatable: Animatable<Float, *>,
    rotationAnimatable: Animatable<Float, *>,
    targetOffsetX: Float,
    targetRotation: Float = if (targetOffsetX > 0) 20f else -20f,
    onAnimationEnd: () -> Unit = {}
) {
    offsetAnimatable.animateTo(targetOffsetX, AnimationSpecs.SpringDamping)
    rotationAnimatable.animateTo(targetRotation, AnimationSpecs.FastAnimation)
    onAnimationEnd()
}

/**
 * Bounce animation for buttons
 *
 * @param targetScale Scale factor to animate to
 * @return Animated scale value
 */
@Composable
fun animateBounceScale(targetScale: Float): Float {
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = AnimationSpecs.SpringDamping,
        label = "bounce_scale"
    )
    return scale
}

/**
 * Pulse animation
 * Used for loading states, notifications
 */
@Composable
fun animatePulse(enabled: Boolean = true): Float {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.3f,
        animationSpec = tween(1000),
        label = "pulse_alpha"
    )
    return alpha
}

// Lottie Animation Helper
/**
 * Lottie animation integration example
 * Usage: LottieAnimation(composition, iterations = LottieConstants.IterateForever)
 *
 * Add to build.gradle.kts:
 * implementation("com.airbnb.android:lottie-compose:6.1.0")
 */
object LottieAnimationHelper {
    const val SPLASH_ANIMATION = "animations/splash.json"
    const val MATCH_ANIMATION = "animations/match.json"
    const val LOADING_ANIMATION = "animations/loading.json"

    // Example usage:
    // val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    // LottieAnimation(composition, iterations = LottieConstants.IterateForever)
}

