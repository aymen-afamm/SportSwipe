package com.sportmatch.app.ui.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.theme.*
import kotlinx.coroutines.launch

/**
 * Premium Onboarding Screen
 * 3 screens with big illustration area, small text, dot indicators
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onNavigateToLogin: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            title = "Find Sports Partners",
            description = "Connect with people who share your passion for sports and fitness",
            gradientColors = listOf(GradientBlue, GradientPurple)
        ),
        OnboardingPage(
            title = "Swipe & Match",
            description = "Swipe right to like, left to pass, or up for super like",
            gradientColors = listOf(GradientPurple, GradientTeal)
        ),
        OnboardingPage(
            title = "Chat & Connect",
            description = "Start conversations with your matches and plan your next workout together",
            gradientColors = listOf(GradientTeal, GradientBlue)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // ViewPager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(
                page = pages[page],
                modifier = Modifier.fillMaxSize()
            )
        }

        // Bottom Section
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(Spacing.xl)
        ) {
            // Dot Indicators
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = Spacing.lg),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                repeat(3) { index ->
                    val isSelected = pagerState.currentPage == index
                    val width = if (isSelected) 24.dp else 8.dp
                    val alpha = if (isSelected) 1f else 0.4f

                    Box(
                        modifier = Modifier
                            .width(width)
                            .height(8.dp)
                            .alpha(alpha)
                            .background(
                                color = if (isSelected) Primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                shape = CircleShape
                            )
                    )
                }
            }

            // Get Started Button
            PrimaryButton(
                text = if (pagerState.currentPage == 2) "Get Started" else "Next",
                onClick = {
                    if (pagerState.currentPage < 2) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onNavigateToLogin()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: OnboardingPage,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = page.gradientColors
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Illustration Placeholder (Big Area)
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .background(
                        color = TextPrimaryDark.copy(alpha = 0.15f),
                        shape = MaterialTheme.shapes.extraLarge
                    )
            )

            Spacer(modifier = Modifier.height(Spacing.xxxl))

            // Title
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Description
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimaryDark.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = Spacing.lg)
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

private data class OnboardingPage(
    val title: String,
    val description: String,
    val gradientColors: List<Color>
)