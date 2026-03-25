package com.silkfinik.vinylcatalog.ui.theme

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun Modifier.glassBackground(
    color: Color,
    alpha: Float = 0.8f
): Modifier = this.background(color.copy(alpha = alpha))


fun Modifier.recordLabelGradientBackground(
    startColor: Color,
    endColor: Color
): Modifier = this.background(
    brush = Brush.linearGradient(
        colors = listOf(startColor, endColor)
    )
)
