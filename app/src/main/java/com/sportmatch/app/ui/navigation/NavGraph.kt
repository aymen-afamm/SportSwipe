 package com.sportmatch.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object UploadPhotos : Screen("upload_photos")
    object Swipe : Screen("swipe")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object ChatList : Screen("chat_list")
    object Chat : Screen("chat/{matchId}") {
        fun createRoute(matchId: String) = "chat/$matchId"
    }
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            navController.navigate(Screen.Swipe.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToSwipe = {
                    navController.navigate(Screen.Swipe.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Swipe.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onSignupSuccess = {
                    navController.navigate(Screen.UploadPhotos.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.UploadPhotos.route) {
            UploadPhotosScreen(
                onPhotosUploaded = {
                    navController.navigate(Screen.Swipe.route) {
                        popUpTo(Screen.UploadPhotos.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Swipe.route) {
            SwipeScreen(
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToChatList = {
                    navController.navigate(Screen.ChatList.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onMatchFound = { matchId ->
                    navController.navigate(Screen.Chat.createRoute(matchId))
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEditProfile = {
                    navController.navigate(Screen.EditProfile.route)
                }
            )
        }

        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ChatList.route) {
            ChatListScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToChat = { matchId ->
                    navController.navigate(Screen.Chat.createRoute(matchId))
                }
            )
        }

        composable(Screen.Chat.route) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId") ?: ""
            ChatScreen(
                matchId = matchId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}

