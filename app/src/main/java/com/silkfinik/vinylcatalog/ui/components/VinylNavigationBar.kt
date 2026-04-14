package com.silkfinik.vinylcatalog.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.silkfinik.vinylcatalog.ui.theme.VinylCatalogTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.unit.dp

@Composable
fun VinylNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)

    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 0.dp,
        content = content
    )
}
data class VinylNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
)

@Composable
fun RowScope.VinylNavigationBarItem(
    item: VinylNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = if (isSelected) item.selectedIcon else item.icon,
                contentDescription = item.title
            )
        },
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelMedium
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun VinylNavigationBarPreview() {
    VinylCatalogTheme {
        VinylNavigationBar {
            val items = listOf(
                VinylNavItem("collection", "Collection", Icons.Outlined.LibraryMusic, Icons.Filled.LibraryMusic),
                VinylNavItem("search", "Search", Icons.Outlined.Search, Icons.Filled.Search),
                VinylNavItem("wishlist", "Wishlist", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite)
            )
            items.forEachIndexed { index, item ->
                VinylNavigationBarItem(
                    item = item,
                    isSelected = index == 0,
                    onClick = {}
                )
            }
        }
    }
}
