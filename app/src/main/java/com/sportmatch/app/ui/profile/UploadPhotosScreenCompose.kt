package com.sportmatch.app.ui.profile

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.DividerLight
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.CornerCard
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.ProfileViewModel

/**
 * UploadPhotosScreen - Photo upload grid for profile creation.
 * Allows users to add 1-5 photos for their profile.
 *
 * @param onNavigateToSwipe Callback when photo upload is complete
 * @param viewModel ProfileViewModel for handling photo upload logic
 */
@Composable
fun UploadPhotosScreen(
    onNavigateToSwipe: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val fadeInAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(600),
        label = "fade_in"
    )

    val photoUrls = listOf(
        "https://example.com/photo1.jpg",
        null,
        null,
        null,
        null
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .alpha(fadeInAlpha)
            .verticalScroll(rememberScrollState())
            .padding(Dimens.screen_padding)
    ) {
        Spacer(modifier = Modifier.height(Dimens.large))

        // Header
        Text(
            text = "Upload Your Photos",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(Dimens.small))

        Text(
            text = "Add 1-5 photos to help others get to know you better",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        // Photo Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(Dimens.large),
            verticalArrangement = Arrangement.spacedBy(Dimens.large),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(5) { index ->
                PhotoSlot(
                    imageUrl = photoUrls.getOrNull(index),
                    onAddPhoto = {
                        // TODO: Handle photo selection from gallery
                    },
                    onRemovePhoto = {
                        // TODO: Handle photo removal
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        // Continue Button
        PrimaryButton(
            text = "Continue",
            onClick = onNavigateToSwipe
        )

        Spacer(modifier = Modifier.height(Dimens.large))
    }
}

@Composable
private fun PhotoSlot(
    imageUrl: String?,
    onAddPhoto: () -> Unit,
    onRemovePhoto: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(CornerCard)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 2.dp,
                color = if (imageUrl != null) Primary else DividerLight,
                shape = CornerCard
            )
            .clickable { onAddPhoto() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add photo",
                modifier = Modifier.size(Dimens.icon_size_extra_large),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadPhotosScreenPreview() {
    SportMatchTheme {
        UploadPhotosScreen(onNavigateToSwipe = { })
    }
}

