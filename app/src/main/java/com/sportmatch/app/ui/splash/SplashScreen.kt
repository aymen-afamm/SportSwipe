package com.sportmatch.app.ui.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.PrimaryDark
import com.sportmatch.app.ui.theme.Secondary
import com.sportmatch.app.ui.theme.Tertiary
import com.sportmatch.app.ui.theme.TextPrimaryDark
import com.sportmatch.app.ui.theme.SportMatchTheme
import kotlinx.coroutines.delay

/**
 * SplashScreen - App entry point with brand animation.
 * Features gradient background, pulsing logo, and auto-navigation based on auth state.
 *
 * @param onNavigateToOnboarding Callback when user is not authenticated
 * @param onNavigateToSwipe Callback when user is authenticated
 */
@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToSwipe: () -> Unit
) {
    // Fade-in animation for logo
    val infiniteTransition = rememberInfiniteTransition(label = "splash_animation")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_animation"
    )

    // Scale animation for logo
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale_animation"
    )

    // Auto-navigate after delay
    LaunchedEffect(Unit) {
        delay(2500)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            onNavigateToSwipe()
        } else {
            onNavigateToOnboarding()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Primary,
                        Tertiary,
                        Secondary
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .alpha(alpha)
                .scale(scale)
        ) {
            // App Logo/Icon placeholder
            Box(
                modifier = Modifier
                    .size(Dimens.avatar_size_extra_large)
                    .background(
                        color = TextPrimaryDark.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Spacer(modifier = Modifier.height(Dimens.large))

            // App Name
            Text(
                text = "SportMatch",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryDark
            )

            Spacer(modifier = Modifier.height(Dimens.small))

            // Tagline
            Text(
                text = "Find your perfect sports partner",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimaryDark.copy(alpha = 0.9f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SportMatchTheme {
        SplashScreen(
            onNavigateToOnboarding = { },
            onNavigateToSwipe = { }
        )
    }
}
