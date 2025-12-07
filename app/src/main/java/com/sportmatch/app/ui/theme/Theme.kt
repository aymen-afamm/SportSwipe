package com.sportmatch.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * SportMatch Light Theme Color Scheme
 */
private val LightColorScheme = ColorScheme(
    primary = Primary,
    onPrimary = TextPrimaryDark,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = TextPrimary,

    secondary = Secondary,
    onSecondary = TextPrimaryDark,
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = TextPrimary,

    tertiary = Tertiary,
    onTertiary = TextPrimaryDark,
    tertiaryContainer = TertiaryLight,
    onTertiaryContainer = TextPrimary,

    error = Error,
    onError = TextPrimaryDark,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Error,

    background = BackgroundLight,
    onBackground = TextPrimary,

    surface = SurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondary,

    outline = DividerLight,
    outlineVariant = Color(0xFFCAC4D0),
    scrim = Scrim
)

/**
 * SportMatch Dark Theme Color Scheme
 */
private val DarkColorScheme = ColorScheme(
    primary = PrimaryLight,
    onPrimary = TextPrimary,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = TextPrimaryDark,

    secondary = SecondaryLight,
    onSecondary = TextPrimary,
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = TextPrimaryDark,

    tertiary = TertiaryLight,
    onTertiary = TextPrimary,
    tertiaryContainer = TertiaryDark,
    onTertiaryContainer = TextPrimaryDark,

    error = ErrorDark,
    onError = TextPrimary,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = ErrorDark,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,

    outline = DividerDark,
    outlineVariant = Color(0xFF49454E),
    scrim = Scrim
)

/**
 * SportMatch Composable Theme - Main entry point for all theme configuration.
 *
 * @param darkTheme Whether to use the dark theme variant. Defaults to system preference.
 * @param dynamicColor Whether to use dynamic (Material You) colors on Android 12+.
 *                      Set to false for consistent branding.
 * @param content The content to render within this theme.
 */
@Composable
fun SportMatchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

