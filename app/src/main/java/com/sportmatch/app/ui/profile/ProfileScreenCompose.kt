package com.sportmatch.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.components.ProfileImage
import com.sportmatch.app.ui.components.SportTag
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.ProfileViewModel

/**
 * ProfileScreen - User profile display.
 * Shows user info, photos, and sports interests with option to edit.
 *
 * @param onNavigateToEditProfile Callback to navigate to edit profile
 * @param onBackClick Callback for back button
 * @param viewModel ProfileViewModel for managing profile state
 */
@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onNavigateToEditProfile) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit"
                )
            }
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // Profile Picture
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large),
            contentAlignment = Alignment.Center
        ) {
            ProfileImage(
                imageUrl = null,
                size = Dimens.avatar_size_extra_large
            )
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // User Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large)
        ) {
            Text(
                text = "Alex, 28",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(Dimens.small))

            Text(
                text = "Basketball lover | Gym enthusiast",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // Bio Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large)
        ) {
            Text(
                text = "Bio",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(Dimens.medium))

            Text(
                text = "I'm passionate about sports and fitness. Looking for like-minded people to play basketball and hit the gym with!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // Sports Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large)
        ) {
            Text(
                text = "Sports",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(Dimens.medium))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SportTag(sport = "Basketball", selected = true)
                SportTag(sport = "Gym", selected = true)
                SportTag(sport = "Running", selected = false)
            }
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // Edit Profile Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large)
        ) {
            PrimaryButton(
                text = "Edit Profile",
                onClick = onNavigateToEditProfile
            )
        }

        Spacer(modifier = Modifier.height(Dimens.large))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    SportMatchTheme {
        ProfileScreen(
            onNavigateToEditProfile = { },
            onBackClick = { }
        )
    }
}

