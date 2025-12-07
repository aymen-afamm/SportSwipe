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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.components.SportTag
import com.sportmatch.app.ui.components.SportTextField
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.ProfileViewModel

/**
 * EditProfileScreen - Edit user profile information.
 * Allows users to update bio, sports, and other profile details.
 *
 * @param onNavigateBack Callback for back button
 * @param viewModel ProfileViewModel for managing profile updates
 */
@Composable
fun EditProfileScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var bio by remember { mutableStateOf("I'm passionate about sports and fitness...") }
    var selectedSports by remember { mutableStateOf(setOf("Basketball", "Gym")) }
    val allSports = listOf("Basketball", "Football", "Running", "Gym", "Tennis", "Cycling")

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
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = "Edit Profile",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Box(modifier = Modifier.weight(1f))
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

            SportTextField(
                value = bio,
                onValueChange = { bio = it },
                placeholder = "Tell others about yourself",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                singleLine = false
            )
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // Sports Selection
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large)
        ) {
            Text(
                text = "Sports Interests",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(Dimens.medium))

            // Sports tags
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.medium)
            ) {
                repeat((allSports.size + 1) / 2) { rowIndex ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.small)
                    ) {
                        val sport1 = allSports.getOrNull(rowIndex * 2)
                        val sport2 = allSports.getOrNull(rowIndex * 2 + 1)

                        if (sport1 != null) {
                            SportTag(
                                sport = sport1,
                                selected = sport1 in selectedSports,
                                onClick = {
                                    selectedSports = if (sport1 in selectedSports) {
                                        selectedSports - sport1
                                    } else {
                                        selectedSports + sport1
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        if (sport2 != null) {
                            SportTag(
                                sport = sport2,
                                selected = sport2 in selectedSports,
                                onClick = {
                                    selectedSports = if (sport2 in selectedSports) {
                                        selectedSports - sport2
                                    } else {
                                        selectedSports + sport2
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            )
                        } else if (sport1 != null) {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        // Save Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large)
        ) {
            PrimaryButton(
                text = "Save Changes",
                onClick = {
                    // TODO: Call viewModel to update profile
                    onNavigateBack()
                }
            )
        }

        Spacer(modifier = Modifier.height(Dimens.large))
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    SportMatchTheme {
        EditProfileScreen(onNavigateBack = { })
    }
}

