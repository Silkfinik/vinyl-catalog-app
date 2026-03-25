package com.silkfinik.vinylcatalog.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val VinylBlack = Color(0xFF000000)
val DeepCharcoal = Color(0xFF1A1C1E)

val RecordLabelOrange = Color(0xFFB02F00)
val RecordLabelOrangeContainer = Color(0xFFFF5722)

val SurfaceBase = Color(0xFFF9F9FB)
val SurfaceContainerLow = Color(0xFFF3F3F5)
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainer = Color(0xFFEEEEF0)
val SurfaceContainerHigh = Color(0xFFE8E8EA)
val SurfaceContainerHighest = Color(0xFFE2E2E4)

val OutlineVariant = Color(0xFFC4C7C7)
val OnSurfaceNeutral = Color(0xFF1A1C1D)


val LightColors = lightColorScheme(
    primary = VinylBlack,
    onPrimary = Color.White,
    primaryContainer = DeepCharcoal,
    onPrimaryContainer = Color.White,
    secondary = RecordLabelOrange,
    onSecondary = Color.White,
    secondaryContainer = RecordLabelOrangeContainer,
    onSecondaryContainer = Color.White,
    surface = SurfaceBase,
    onSurface = OnSurfaceNeutral,
    background = SurfaceBase,
    onBackground = OnSurfaceNeutral,
    surfaceVariant = SurfaceContainerLow,
    onSurfaceVariant = OnSurfaceNeutral,
    outlineVariant = OutlineVariant
)

val DarkSurfaceBase = Color(0xFF121212)
val DarkSurfaceContainerLow = Color(0xFF1A1C1E)
val DarkOutlineVariant = Color(0xFF444748)
val OnSurfaceDarkNeutral = Color(0xFFE2E2E4)

val DarkColors = darkColorScheme(
    primary = Color.White,
    onPrimary = VinylBlack,
    primaryContainer = Color.White,
    onPrimaryContainer = VinylBlack,
    secondary = RecordLabelOrange,
    onSecondary = Color.White,
    secondaryContainer = RecordLabelOrangeContainer,
    onSecondaryContainer = Color.White,
    surface = DarkSurfaceBase,
    onSurface = OnSurfaceDarkNeutral,
    background = DarkSurfaceBase,
    onBackground = OnSurfaceDarkNeutral,
    surfaceVariant = DarkSurfaceContainerLow,
    onSurfaceVariant = OnSurfaceDarkNeutral,
    outlineVariant = DarkOutlineVariant
)