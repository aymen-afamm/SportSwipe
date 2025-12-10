package com.sportmatch.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.sportmatch.app.ui.components.PrimaryButton
import com.sportmatch.app.ui.theme.*

import com.sportmatch.app.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onDeleteAccount: () -> Unit = {},
    onReportUser: () -> Unit = {},
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

        SettingsActionItem("Change Password") { /* TODO */ }
        SettingsActionItem("Privacy Policy") { /* TODO */ }
        SettingsActionItem("Terms of Service") { /* TODO */ }

        Spacer(modifier = Modifier.height(Dimens.extra_large))

        // Danger Zone
        SettingsSectionHeader("Danger Zone")

        SettingsActionItem("Report User", titleColor = Error, onClick = onReportUser)
        SettingsActionItem("Delete Account", titleColor = Error, onClick = onDeleteAccount)

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
private fun SettingsSectionHeader(title: String, modifier: Modifier = Modifier) {
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
            .background(MaterialTheme.colorScheme.surface, shape = CornerCard)
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
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Box(
            modifier = Modifier
                .background(if (enabled) Primary else MaterialTheme.colorScheme.surfaceVariant, shape = CornerCard)
                .padding(horizontal = Dimens.small, vertical = Dimens.extra_small)
        ) {
            Text(
                if (enabled) "ON" else "OFF",
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
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = CornerCard)
            .clickable(onClick = onClick)
            .padding(Dimens.medium)
            .padding(horizontal = Dimens.large),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = titleColor)
        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreviewFinal() {
    SportMatchTheme {
        SettingsScreen(onBackClick = {}, onLogoutSuccess = {})
    }
}
