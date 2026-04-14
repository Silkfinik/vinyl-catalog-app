package com.silkfinik.vinylcatalog.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.silkfinik.vinylcatalog.domain.model.VinylRecord
import com.silkfinik.vinylcatalog.ui.theme.VinylCatalogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailsScreen(
    recordId: String,
    onBackClick: () -> Unit,
    viewModel: RecordDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(recordId) {
        viewModel.loadRecord(recordId)
    }
    
    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onBackClick() // dismiss screen on save
        }
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }
        return
    }

    val record = uiState.record
    if (record == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Record not found", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBackClick) { Text("Back") }
        }
        return
    }

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 120.dp) // breathing room for FAB
        ) {
            // Hero Section: Album Cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(353.dp)
            ) {
                AsyncImage(
                    model = record.coverUrl,
                    contentDescription = "Cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().background(Color.Black)
                )
                
                // Back Button
                Box(modifier = Modifier.padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 16.dp, start = 16.dp)) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.2f), CircleShape)
                            .clip(CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }

                // Tonal Overlay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(176.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface.copy(alpha = 0.4f), MaterialTheme.colorScheme.surface)
                            )
                        )
                )
            }

            // Content Canvas (Overlaps the hero image)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-48).dp)
                    .padding(horizontal = 24.dp)
            ) {
                // Title and Artist
                Column(modifier = Modifier.padding(bottom = 32.dp)) {
                    Text(
                        text = record.title,
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = record.artist.uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Metadata Grid
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(
                        modifier = Modifier.weight(1f).background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(8.dp)).padding(16.dp)
                    ) {
                        Text("YEAR", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text(record.year ?: "—", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Column(
                        modifier = Modifier.weight(2f).background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(8.dp)).padding(16.dp)
                    ) {
                        Text("LABEL", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text(record.label ?: "—", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainerLow, RoundedCornerShape(8.dp)).padding(16.dp)
                ) {
                    Column {
                        Text("GENRE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                        Text(record.genre ?: "—", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.height(48.dp))

                // Vinyl Format Editable Action Chips
                Text("VINYL FORMAT", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))
                val formats = listOf("LP", "2xLP", "7\" EP")
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    formats.forEach { formatStr ->
                        val isSelected = uiState.selectedFormat == formatStr
                        Surface(
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHighest,
                            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = CircleShape,
                            modifier = Modifier.clickable { viewModel.updateFormat(formatStr) }
                        ) {
                            Text(
                                text = formatStr,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Pressing Quality Rating Interactive Widget
                Text("PRESSING QUALITY", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = if (i <= uiState.rating) Icons.Default.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Star $i",
                            tint = if (i <= uiState.rating) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier.size(40.dp).clickable { viewModel.updateRating(i) }
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("${uiState.rating}.0", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Personal Notes Interactive Input
                Text("LINER NOTES & CONDITION", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = uiState.notes,
                    onValueChange = { viewModel.updateNotes(it) },
                    placeholder = { Text("Add your personal notes about this copy...", color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                        focusedBorderColor = MaterialTheme.colorScheme.secondary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f)
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Floating Save Button Action
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 32.dp, bottom = 40.dp)
        ) {
            IconButton(
                onClick = { viewModel.saveChanges() },
                modifier = Modifier
                    .size(80.dp)
                    .shadow(16.dp, CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(MaterialTheme.colorScheme.secondary, Color(0xFFFF5722)) // Editor gradient #b02f00 to #ff5722
                        ),
                        CircleShape
                    )
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save", tint = Color.White, modifier = Modifier.size(40.dp))
            }
        }
    }
}
