package com.sportmatch.app.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * SportMatch Icon Set - Centralized icon references.
 * Uses Material Icons + custom icon definitions.
 */

object SportMatchIcons {
    // Navigation Icons
    val Back = Icons.Filled.ArrowBack
    val Close = Icons.Filled.Close
    val Menu = Icons.Filled.MoreVert  // Menu icon

    // Action Icons
    val Like = Icons.Filled.Favorite
    val Pass = Icons.Filled.ThumbDown
    val SuperLike = Icons.Filled.Star
    val Edit = Icons.Filled.Edit
    val Send = Icons.Filled.Send
    val Search = Icons.Filled.Search

    // Profile Icons
    val Person = Icons.Filled.Person
    val Settings = Icons.Filled.Settings
    val Logout = Icons.Filled.Logout

    // Chat Icons
    val Message = Icons.Filled.Message
    val Chat = Icons.Filled.Chat

    // Sports Icons - Material alternatives
    val Sports = Icons.Filled.FitnessCenter
    val Location = Icons.Filled.LocationOn

    // Status Icons
    val Check = Icons.Filled.Check
    val Error = Icons.Filled.Error
    val Warning = Icons.Filled.Warning
    val Info = Icons.Filled.Info

    // Media Icons
    val AddPhoto = Icons.Filled.AddAPhoto
    val Image = Icons.Filled.Image
    val Camera = Icons.Filled.PhotoCamera

    // UI Icons
    val ChevronRight = Icons.Filled.ChevronRight
    val ChevronLeft = Icons.Filled.ChevronLeft
    val Add = Icons.Filled.Add
    val More = Icons.Filled.MoreVert
}

/**
 * Custom icon definitions for Sport-specific icons.
 * These would typically be replaced with actual SVG/ImageVector implementations.
 */
object SportIcons {
    // Sport categories - using Material icons as placeholders
    val Basketball = Icons.Filled.FitnessCenter  // Replace with custom SVG
    val Football = Icons.Filled.FitnessCenter    // Replace with custom SVG
    val Running = Icons.Filled.DirectionsRun
    val Gym = Icons.Filled.FitnessCenter
    val Tennis = Icons.Filled.FitnessCenter      // Placeholder
    val Cycling = Icons.Filled.DirectionsBike
    val Swimming = Icons.Filled.FitnessCenter    // Placeholder
}

