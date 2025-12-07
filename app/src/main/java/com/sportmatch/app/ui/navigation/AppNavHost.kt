package com.sportmatch.app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.sportmatch.app.ui.auth.LoginScreen
import com.sportmatch.app.ui.auth.SignupScreen
import com.sportmatch.app.ui.chat.ChatListScreen
import com.sportmatch.app.ui.chat.ChatScreen
import com.sportmatch.app.ui.onboarding.OnboardingScreen
import com.sportmatch.app.ui.profile.EditProfileScreen
import com.sportmatch.app.ui.profile.ProfileScreen
import com.sportmatch.app.ui.profile.UploadPhotosScreen
import com.sportmatch.app.ui.settings.SettingsScreen
import com.sportmatch.app.ui.splash.SplashScreen
import com.sportmatch.app.ui.swipe.SwipeScreen

/**
 * AppNavHost - Main navigation container for the SportMatch app.
 * Manages navigation between all screens with animated transitions.
 *
 * @param navController Navigation controller (auto-created if not provided)
 * @param startDestination Starting route (default: Splash)
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoutes.SPLASH
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    // Auto-navigate authenticated users past auth screens
    LaunchedEffect(currentUser) {
        if (currentUser != null &&
            (navController.currentDestination?.route == NavRoutes.SPLASH ||
             navController.currentDestination?.route == NavRoutes.LOGIN ||
             navController.currentDestination?.route == NavRoutes.SIGNUP)) {
            navController.navigate(NavRoutes.SWIPE) {
                popUpTo(NavRoutes.SPLASH) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
        }
    ) {
        // Splash Screen
        composable(NavRoutes.SPLASH) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(NavRoutes.ONBOARDING) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                },
                onNavigateToSwipe = {
                    navController.navigate(NavRoutes.SWIPE) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        // Onboarding Screen
        composable(NavRoutes.ONBOARDING) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(NavRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // Login Screen
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                onNavigateToSignup = {
                    navController.navigate(NavRoutes.SIGNUP)
                },
                onLoginSuccess = {
                    navController.navigate(NavRoutes.SWIPE) {
                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Signup Screen
        composable(NavRoutes.SIGNUP) {
            SignupScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onSignupSuccess = {
                    navController.navigate(NavRoutes.UPLOAD_PHOTOS) {
                        popUpTo(NavRoutes.SIGNUP) { inclusive = true }
                    }
                }
            )
        }

        // Upload Photos Screen
        composable(NavRoutes.UPLOAD_PHOTOS) {
            UploadPhotosScreen(
                onNavigateToSwipe = {
                    navController.navigate(NavRoutes.SWIPE) {
                        popUpTo(NavRoutes.UPLOAD_PHOTOS) { inclusive = true }
                    }
                }
            )
        }

        // Swipe Screen (Main Discovery)
        composable(NavRoutes.SWIPE) {
            SwipeScreen(
                onNavigateToMatch = { matchId ->
                    navController.navigate(NavRoutes.chatRoute(matchId))
                },
                onNavigateToProfile = {
                    navController.navigate(NavRoutes.PROFILE)
                },
                onNavigateToMatches = {
                    navController.navigate(NavRoutes.MATCHES_LIST)
                },
                onNavigateToSettings = {
                    navController.navigate(NavRoutes.SETTINGS)
                }
            )
        }

        // Profile Screen
        composable(NavRoutes.PROFILE) {
            ProfileScreen(
                onNavigateToEditProfile = {
                    navController.navigate(NavRoutes.EDIT_PROFILE)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Edit Profile Screen
        composable(NavRoutes.EDIT_PROFILE) {
            EditProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Matches List Screen
        composable(NavRoutes.MATCHES_LIST) {
            ChatListScreen(
                onNavigateToChat = { matchId ->
                    navController.navigate(NavRoutes.chatRoute(matchId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Chat Screen
        composable(
            route = NavRoutes.CHAT,
            arguments = listOf(
                androidx.navigation.navArgument("matchId") {
                    type = androidx.navigation.NavType.StringType
                }
            )
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId") ?: ""
            ChatScreen(
                matchId = matchId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Settings Screen
        composable(NavRoutes.SETTINGS) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onLogoutSuccess = {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

