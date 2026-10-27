package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val VendeMasDarkColorScheme = darkColorScheme(
    primary = ElectricCyan,
    onPrimary = DeepBlueDark,
    primaryContainer = DeepBlueSurface,
    onPrimaryContainer = ElectricCyan,
    secondary = ElectricCyan,
    onSecondary = DeepBlueDark,
    tertiary = ElectricCyanDark,
    background = DeepBlueDark,
    onBackground = BrandWhite,
    surface = DeepBlue,
    onSurface = BrandWhite,
    surfaceVariant = DeepBlueSurface,
    onSurfaceVariant = LightGray,
    outline = BorderGray
)

private val VendeMasLightColorScheme = lightColorScheme(
    primary = DeepBlue,
    onPrimary = BrandWhite,
    primaryContainer = ElectricCyanSoft,
    onPrimaryContainer = DeepBlue,
    secondary = ElectricCyan,
    onSecondary = DeepBlue,
    secondaryContainer = ElectricCyanSoft,
    onSecondaryContainer = DeepBlueDark,
    tertiary = DarkGray,
    background = LightGray,
    onBackground = DarkGray,
    surface = BrandWhite,
    onSurface = DarkGray,
    surfaceVariant = Color(0xFFEDF2F7),
    onSurfaceVariant = TextMuted,
    outline = BorderGray
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) VendeMasDarkColorScheme else VendeMasLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
