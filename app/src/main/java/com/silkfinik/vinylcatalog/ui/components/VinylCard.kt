package com.silkfinik.vinylcatalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import com.silkfinik.vinylcatalog.ui.theme.VinylCatalogTheme

/**
 * The "Sleeve" Card. 
 * Uses large corners, no borders, and relies on color tonal shift for depth.
 */
@Composable
fun VinylCard(
    title: String,
    artist: String,
    coverUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // According to DESIGN.md: surface_container_lowest, radius: lg (2rem/32dp), no shadow/dividers
    val surfaceContainerLowest = MaterialTheme.colorScheme.surface // Simplified for fallback, actual is defined in Theme
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Using absolute fallback or actual token
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            // Square image logic
            AsyncImage(
                model = coverUrl ?: "", // Provide a default placeholder if needed
                contentDescription = "Album cover: $title",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Square
                    .clip(MaterialTheme.shapes.large) // Clip top corners
            )
            
            Spacer(modifier = Modifier.height(16.dp)) // spacing-4 for separating image and metadata
            
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VinylCardPreview() {
    VinylCatalogTheme {
        Box(modifier = Modifier.padding(16.dp).background(MaterialTheme.colorScheme.surface)) {
            VinylCard(
                title = "Dark Side of the Moon",
                artist = "Pink Floyd",
                coverUrl = null,
                onClick = {}
            )
        }
    }
}
