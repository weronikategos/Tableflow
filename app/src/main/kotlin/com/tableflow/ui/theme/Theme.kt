package com.tableflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFFB3410C),
    secondary = Color(0xFF6F5B3E),
    background = Color(0xFFFFFBF6)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFB59D),
    secondary = Color(0xFFD9C2A0),
    background = Color(0xFF201A16)
)

@Composable
fun TableFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
