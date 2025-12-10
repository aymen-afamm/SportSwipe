package com.sportmatch.app.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sportmatch.app.ui.theme.SportMatchTheme

@Preview(showBackground = true)
@Composable
fun SettingsScreenOnlyPreview() {
    SportMatchTheme {
        SettingsScreen(
            onBackClick = {},
            onLogoutSuccess = {},
            onDeleteAccount = {},
            onReportUser = {}
        )
    }
}
