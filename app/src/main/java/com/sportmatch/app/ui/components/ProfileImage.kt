package com.sportmatch.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.SportMatchTheme

/**
 * ProfileImage Component - Circular avatar with image loading and placeholder.
 *
 * @param imageUrl URL of the image to load
 * @param modifier Modifier for sizing
 * @param size Size of the avatar (default: large)
 * @param borderColor Color of the border around the avatar
 * @param borderWidth Width of the border
 * @param contentDescription Accessibility description
 */
@Composable
fun ProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = Dimens.avatar_size_large,
    borderColor: Color = Primary,
    borderWidth: Dp = 2.dp,
    contentDescription: String = "Profile picture"
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                modifier = Modifier.size(size),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(size)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(size * 0.5f),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImagePreview() {
    SportMatchTheme {
        ProfileImage(
            imageUrl = null,
            size = Dimens.avatar_size_extra_large
        )
    }
}

