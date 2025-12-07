package com.sportmatch.app.ui.theme

import androidx.compose.ui.unit.dp

/**
 * SportMatch Dimension System - Centralized spacing and sizing constants.
 * Follows Material 3 spacing scale (4dp base unit).
 */

// Spacing Increments
object Dimens {
    // Base spacing unit
    val spacing_1 = 4.dp
    val spacing_2 = 8.dp
    val spacing_3 = 12.dp
    val spacing_4 = 16.dp
    val spacing_5 = 20.dp
    val spacing_6 = 24.dp
    val spacing_7 = 28.dp
    val spacing_8 = 32.dp
    val spacing_9 = 36.dp
    val spacing_10 = 40.dp

    // Aliases for common use cases
    val extra_small = spacing_1    // 4dp
    val small = spacing_2          // 8dp
    val medium = spacing_4         // 16dp
    val large = spacing_6          // 24dp
    val extra_large = spacing_8    // 32dp

    // Component-specific dimensions
    // Buttons
    val button_height = 48.dp
    val button_height_small = 40.dp
    val button_padding_horizontal = spacing_6
    val button_padding_vertical = spacing_3

    // Input Fields
    val textfield_height = 48.dp
    val textfield_height_small = 40.dp
    val textfield_padding = spacing_4

    // Cards
    val card_padding = spacing_4
    val card_elevation = 4.dp
    val card_elevation_pressed = 8.dp

    // Icons
    val icon_size_small = 16.dp
    val icon_size_medium = 24.dp
    val icon_size_large = 32.dp
    val icon_size_extra_large = 48.dp

    // Avatar/Profile Images
    val avatar_size_small = 32.dp
    val avatar_size_medium = 48.dp
    val avatar_size_large = 64.dp
    val avatar_size_extra_large = 120.dp

    // Swipe Card
    val swipe_card_height = 480.dp
    val swipe_card_corner_radius = 16.dp

    // Sport Tag
    val tag_height = 32.dp
    val tag_padding_horizontal = spacing_2
    val tag_padding_vertical = spacing_1

    // Page Indicator
    val indicator_dot_size = 8.dp
    val indicator_dot_size_active = 8.dp
    val indicator_spacing = spacing_2

    // Dialog
    val dialog_padding = spacing_6
    val dialog_corner_radius = 16.dp

    // Divider
    val divider_height = 1.dp
    val divider_padding = spacing_4

    // Bottom Navigation
    val bottom_nav_height = 80.dp

    // Chat Bubble
    val chat_bubble_max_width = 300.dp
    val chat_bubble_corner_radius = 12.dp

    // Screen padding
    val screen_padding = spacing_6
    val screen_padding_horizontal = spacing_6
    val screen_padding_vertical = spacing_6
}

// Top-level convenience accessors
val xs = Dimens.extra_small
val sm = Dimens.small
val md = Dimens.medium
val lg = Dimens.large
val xl = Dimens.extra_large

