package com.sportmatch.app.ui.navigation

/**
 * Navigation route constants for the SportMatch app.
 * Centralized route definitions to avoid string duplication.
 */

object NavRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val UPLOAD_PHOTOS = "upload_photos"
    const val SWIPE = "swipe"
    const val MATCH = "match"
    const val MATCHES_LIST = "matches_list"
    const val PROFILE = "profile"
    const val EDIT_PROFILE = "edit_profile"
    const val CHAT = "chat/{matchId}"
    const val CHAT_LIST = "chat_list"
    const val SETTINGS = "settings"

    // Helper to build chat route with ID
    fun chatRoute(matchId: String): String = "chat/$matchId"
}

