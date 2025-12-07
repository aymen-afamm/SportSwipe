package com.sportmatch.app.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * Preview composable demonstrating the complete SportMatch theme.
 * Shows colors, typography, and component styling.
 */

@Preview(showBackground = true)
@Composable
fun ThemePreview() {
    SportMatchTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.large)
        ) {
            // Colors Preview
            Text("Colors", style = MaterialTheme.typography.headlineLarge)

            ColorRow("Primary", MaterialTheme.colorScheme.primary)
            ColorRow("Secondary", MaterialTheme.colorScheme.secondary)
            ColorRow("Tertiary", MaterialTheme.colorScheme.tertiary)
            ColorRow("Error", MaterialTheme.colorScheme.error)
            ColorRow("Success", Success)

            // Typography Preview
            Text("Typography", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(top = Dimens.large))

            Text("Display Large", style = MaterialTheme.typography.displayLarge)
            Text("Headline Large", style = MaterialTheme.typography.headlineLarge)
            Text("Title Large", style = MaterialTheme.typography.titleLarge)
            Text("Body Large", style = MaterialTheme.typography.bodyLarge)
            Text("Label Large", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun ColorRow(label: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.small),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .background(color)
                .padding(Dimens.small)
        ) {
            // Empty for color display
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DarkThemePreview() {
    SportMatchTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.large)
        ) {
            Text("Dark Theme Preview", style = MaterialTheme.typography.headlineLarge)
            Text(
                "This demonstrates the dark color scheme",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

