package com.example.moodjournal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = LightCoral,
    secondary = LightLav,
    tertiary = LightMint,
    background = LightPageBg1,
    surface = LightCardBg,
    onBackground = LightText1,
    onSurface = LightText1,
    outline = LightCardBorder
)

private val DarkColors = darkColorScheme(
    primary = DarkCoral,
    secondary = DarkLav,
    tertiary = DarkMint,
    background = DarkPageBg1,
    surface = DarkCardBg,
    onBackground = DarkText1,
    onSurface = DarkText1,
    outline = DarkCardBorder
)

@Composable
fun MoodJournalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
