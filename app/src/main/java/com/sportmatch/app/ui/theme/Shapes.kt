package com.sportmatch.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * SportMatch Shape System - Defines rounded corner shapes used throughout the app.
 * Follows Material 3 shape spec with custom SportMatch radius values.
 */

val AppShapes = Shapes(
    // Extra small: 4dp - Used for subtle elements
    extraSmall = RoundedCornerShape(4.dp),

    // Small: 8dp - Used for minor components
    small = RoundedCornerShape(8.dp),

    // Medium: 12dp - Used for cards, chips
    medium = RoundedCornerShape(12.dp),

    // Large: 16dp - Used for larger cards, dialogs
    large = RoundedCornerShape(16.dp),

    // Extra Large: 28dp - Used for buttons, large rounded elements
    extraLarge = RoundedCornerShape(28.dp)
)

// Custom shape definitions for specific use cases
val CornerSmall = RoundedCornerShape(4.dp)
val CornerMedium = RoundedCornerShape(8.dp)
val CornerCard = RoundedCornerShape(12.dp)
val CornerLarge = RoundedCornerShape(16.dp)
val CornerButton = RoundedCornerShape(28.dp)
val CornerFull = RoundedCornerShape(50.dp)  // Fully rounded (pills, circles)

