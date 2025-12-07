package com.sportmatch.app.ui.theme

import androidx.compose.material3.ElevationDefaults
import androidx.compose.ui.unit.dp

// ============================================
// PREMIUM ELEVATION SYSTEM
// ============================================
// Soft shadows for depth

object Elevation {
    val none = 0.dp
    val xs = 2.dp      // Subtle separation
    val sm = 4.dp      // Cards
    val md = 8.dp      // Elevated cards
    val lg = 12.dp     // Floating elements
    val xl = 16.dp     // Modals, dialogs
    val xxl = 24.dp    // Maximum elevation
}

// Material 3 elevation defaults
val CardElevation = ElevationDefaults.cardElevation(
    defaultElevation = Elevation.sm,
    pressedElevation = Elevation.xs,
    hoveredElevation = Elevation.md,
    focusedElevation = Elevation.md,
    draggedElevation = Elevation.lg
)