package com.kaushal.worker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val Colors = lightColorScheme(
    primary = KaushalOrange,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = KaushalNavy,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    background = KaushalCream,
    surface = androidx.compose.ui.graphics.Color.White,
    onBackground = KaushalText,
    onSurface = KaushalText
)

@Composable
fun KaushalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Colors,
        typography = KaushalTypography,
        content = content
    )
}
