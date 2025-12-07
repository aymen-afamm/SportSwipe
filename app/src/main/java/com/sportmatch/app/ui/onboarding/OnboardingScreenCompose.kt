package com.sportmatch.app.ui.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import com.sportmatch.app.ui.components.PageIndicator
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.Secondary
import com.sportmatch.app.ui.theme.Tertiary
import com.sportmatch.app.ui.theme.TextPrimaryDark
import com.sportmatch.app.ui.theme.SportMatchTheme

data class OnboardingPage(
    val title: String,
    val description: String,
    val gradient: List<androidx.compose.ui.graphics.Color>
)

/**
 * OnboardingScreen - Multi-page onboarding introduction.
 * Displays app features using a horizontal pager with indicators.
 *
 * @param onNavigateToLogin Callback when onboarding is complete
 */
@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit
) {
    val pages = listOf(
        OnboardingPage(
            title = "Find Sports Partners",
            description = "Connect with people who share your passion for sports and fitness in your area",
            gradient = listOf(Primary, Tertiary)
        ),
        OnboardingPage(
            title = "Swipe & Match",
            description = "Swipe right to like, left to pass, or up for super like other athletes and sports enthusiasts",
            gradient = listOf(Secondary, Primary)
        ),
        OnboardingPage(
            title = "Chat & Connect",
            description = "Start conversations with your matches and plan your next game or workout together",
            gradient = listOf(Tertiary, Secondary)
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    val fadeInAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600),
        label = "fade_in"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val page = pages[pageIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = page.gradient
                        )
                    )
                    .padding(Dimens.screen_padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(0.3f))

                // Decorative icon/illustration area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.avatar_size_extra_large)
                        .background(
                            color = TextPrimaryDark.copy(alpha = 0.15f),
                            shape = MaterialTheme.shapes.extraLarge
                        )
                )

                Spacer(modifier = Modifier.height(Dimens.extra_large))

                // Page Title
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(Dimens.medium))

                // Page Description
                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimaryDark.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))

                // Page Indicator
                PageIndicator(
                    pageCount = pages.size,
                    currentPage = pageIndex,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(Dimens.extra_large))

                // Navigation Buttons
                if (pageIndex < pages.size - 1) {
                    // Next Button
                    PrimaryButton(
                        text = "Next",
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pageIndex + 1)
                            }
                        }
                    )
                } else {
                    // Get Started Button
                    PrimaryButton(
                        text = "Get Started",
                        onClick = onNavigateToLogin
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.large))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    SportMatchTheme {
        OnboardingScreen(onNavigateToLogin = { })
    }
}

