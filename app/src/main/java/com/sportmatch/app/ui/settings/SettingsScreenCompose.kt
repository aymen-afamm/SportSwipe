package com.sportmatch.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ChevronRight
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
import com.sportmatch.app.ui.theme.Dimens
import com.sportmatch.app.ui.theme.Primary
import com.sportmatch.app.ui.theme.Error
import com.sportmatch.app.ui.theme.CornerCard
import com.sportmatch.app.ui.theme.SportMatchTheme
import com.sportmatch.app.ui.viewmodel.SettingsViewModel

/**
 * SettingsScreen - App settings and account management.
 * Provides options for notifications, appearance, and account actions.
 *
 * @param onBackClick Callback for back button
 * @param onLogoutSuccess Callback when logout completes successfully
 * @param viewModel SettingsViewModel for managing settings
 */
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var darkModeEnabled by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

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
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Box(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(Dimens.large))

        // Appearance Section
        SettingsSectionHeader("Appearance")

        SettingsToggleItem(
            title = "Dark Mode",
            subtitle = "Use dark theme",
            enabled = darkModeEnabled,
            onToggle = { darkModeEnabled = !darkModeEnabled }
        )

        Spacer(modifier = Modifier.height(Dimens.large))

        // Notifications Section
        SettingsSectionHeader("Notifications")

        SettingsToggleItem(
            title = "Push Notifications",
            subtitle = "Receive notifications for matches and messages",
            enabled = notificationsEnabled,
            onToggle = { notificationsEnabled = !notificationsEnabled }
        )

        Spacer(modifier = Modifier.height(Dimens.large))

        // Account Section
        SettingsSectionHeader("Account")

        SettingsActionItem(
            title = "Change Password",
            onClick = { /* TODO: Navigate to change password */ }
        )

        SettingsActionItem(
            title = "Privacy Policy",
            onClick = { /* TODO: Open privacy policy */ }
        )

        SettingsActionItem(
            title = "Terms of Service",
            onClick = { /* TODO: Open terms */ }
        )

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        // Danger Zone
        SettingsSectionHeader("Danger Zone")

        SettingsActionItem(
            title = "Report User",
            titleColor = Error,
            onClick = { /* TODO: Open report dialog */ }
        )

        SettingsActionItem(
            title = "Delete Account",
            titleColor = Error,
            onClick = { /* TODO: Show confirmation dialog */ }
        )

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        // Logout Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.large)
        ) {
            PrimaryButton(
                text = "Logout",
                onClick = {
                    viewModel.logout()
                    onLogoutSuccess()
                }
            )
        }

        Spacer(modifier = Modifier.height(Dimens.large))
    }
}

@Composable
private fun SettingsSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = Primary,
        modifier = modifier.padding(horizontal = Dimens.large)
    )
}

@Composable
private fun SettingsToggleItem(
    title: String,
    subtitle: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CornerCard
            )
            .clickable { onToggle(!enabled) }
            .padding(Dimens.medium)
            .padding(horizontal = Dimens.large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.extra_small)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Simple toggle indicator
        Box(
            modifier = Modifier
                .background(
                    color = if (enabled) Primary else MaterialTheme.colorScheme.surfaceVariant,
                    shape = CornerCard
                )
                .padding(horizontal = Dimens.small, vertical = Dimens.extra_small)
        ) {
            Text(
                text = if (enabled) "ON" else "OFF",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingsActionItem(
    title: String,
    titleColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CornerCard
            )
            .clickable(onClick = onClick)
            .padding(Dimens.medium)
            .padding(horizontal = Dimens.large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = titleColor
        )
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SportMatchTheme {
        SettingsScreen(
            onBackClick = { },
            onLogoutSuccess = { }
        )
    }
}

