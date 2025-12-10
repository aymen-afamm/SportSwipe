package com.sportmatch.app.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning

/**
 * SportMatch Icon Set - Centralized icon references.
 * Uses Material Icons + custom icon definitions.
 */

object SportMatchIcons {
    // Navigation Icons
    val Back = Icons.Filled.ArrowBack
    val Close = Icons.Filled.Close
    val Menu = Icons.Filled.MoreVert

    // Action Icons
    val Like = Icons.Filled.Favorite
    val Pass = Icons.Filled.Close
    val SuperLike = Icons.Filled.Star
    val Edit = Icons.Filled.Edit
    val Send = Icons.Filled.Send
    val Search = Icons.Filled.Close

    // Profile Icons
    val Person = Icons.Filled.Person
    val Settings = Icons.Filled.Settings
    val Logout = Icons.Filled.ArrowBack

    // Chat Icons
    val Message = Icons.Filled.Send
    val Chat = Icons.Filled.Send

    // Sports Icons - Material alternatives
    val Sports = Icons.Filled.Star
    val Location = Icons.Filled.LocationOn

    // Status Icons
    val Check = Icons.Filled.Check
    val Error = Icons.Filled.Warning
    val Warning = Icons.Filled.Warning
    val Info = Icons.Filled.Info

    // Media Icons
    val AddPhoto = Icons.Filled.Add
    val Image = Icons.Filled.Add
    val Camera = Icons.Filled.Add

    // UI Icons
    val ChevronRight = Icons.Filled.ArrowBack
    val ChevronLeft = Icons.Filled.ArrowBack
    val Add = Icons.Filled.Add
    val More = Icons.Filled.MoreVert
}

/**
 * Custom icon definitions for Sport-specific icons.
 * These would typically be replaced with actual SVG/ImageVector implementations.
 */
object SportIcons {
    // Sport categories - using Material icons as placeholders
    val Basketball = Icons.Filled.Star
    val Football = Icons.Filled.Star
    val Running = Icons.Filled.Star
    val Gym = Icons.Filled.Star
    val Tennis = Icons.Filled.Star
    val Cycling = Icons.Filled.Star
    val Swimming = Icons.Filled.Star
}

