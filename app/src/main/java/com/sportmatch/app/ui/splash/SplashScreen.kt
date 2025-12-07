package com.sportmatch.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.ui.theme.*
import kotlinx.coroutines.delay

/**
 * Premium Splash Screen
 * Centered logo, subtle gradient background, fade-in animation
 */
@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToSwipe: () -> Unit
) {
    // Fade-in animation
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

    // Logo scale animation
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale_animation"
    )

    LaunchedEffect(Unit) {
        delay(2500) // Premium delay for brand recognition
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
                        GradientStart,
                        GradientMiddle,
                        GradientEnd
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
                    .size(120.dp)
                    .background(
                        color = TextOnPrimary.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            // App Name
            Text(
                text = "SportMatch",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = TextOnPrimary
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            // Tagline
            Text(
                text = "Find your perfect sports partner",
                style = MaterialTheme.typography.bodyLarge,
                color = TextOnPrimary.copy(alpha = 0.9f)
            )
        }
    }
}