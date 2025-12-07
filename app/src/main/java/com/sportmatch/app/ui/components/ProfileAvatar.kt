package com.sportmatch.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sportmatch.app.ui.theme.*

/**
 * Premium Profile Avatar Component
 * Circular with optional gradient border
 */
@Composable
fun ProfileAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Medium,
    showGradientBorder: Boolean = false
) {
    val avatarSize = when (size) {
        AvatarSize.Small -> Spacing.avatarSmall
        AvatarSize.Medium -> Spacing.avatarMedium
        AvatarSize.Large -> Spacing.avatarLarge
        AvatarSize.XLarge -> Spacing.avatarXLarge
    }

    Box(
        modifier = modifier.size(avatarSize),
        contentAlignment = Alignment.Center
    ) {
        if (showGradientBorder) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Primary, SportAccent, PurpleAccent)
                        ),
                        shape = CircleShape
                    )
                    .padding(3.dp)
            ) {
                AsyncImage(
                    model = imageUrl ?: "",
                    contentDescription = "Profile avatar",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            AsyncImage(
                model = imageUrl ?: "",
                contentDescription = "Profile avatar",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    ),
                contentScale = ContentScale.Crop
            )
        }
    }
}

enum class AvatarSize {
    Small,
    Medium,
    Large,
    XLarge
}