package com.silkfinik.vinylcatalog.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.outlined.Album
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.silkfinik.vinylcatalog.ui.components.VinylNavigationBar
import com.silkfinik.vinylcatalog.ui.components.VinylNavigationBarItem
import com.silkfinik.vinylcatalog.ui.components.VinylNavItem
import com.silkfinik.vinylcatalog.ui.screens.collection.MyCollectionScreen
import com.silkfinik.vinylcatalog.ui.screens.details.RecordDetailsScreen
import com.silkfinik.vinylcatalog.ui.screens.search.SearchReleaseScreen
import com.silkfinik.vinylcatalog.ui.screens.wishlist.WishlistScreen

sealed class Screen(val route: String) {
    object Collection : Screen("collection")
    object Search : Screen("search")
    object Wishlist : Screen("wishlist")
    object Details : Screen("details/{recordId}") {
        fun createRoute(recordId: String) = "details/$recordId"
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarScreens = listOf(Screen.Collection.route, Screen.Search.route, Screen.Wishlist.route)
    val showBottomBar = currentRoute in bottomBarScreens

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                VinylNavigationBar {
                    val items = listOf(
                        VinylNavItem(Screen.Collection.route, "Collection", Icons.Outlined.Album, Icons.Default.Album),
                        VinylNavItem(Screen.Search.route, "Search", Icons.Outlined.Explore, Icons.Default.Explore),
                        VinylNavItem(Screen.Wishlist.route, "Wishlist", Icons.Outlined.AutoAwesome, Icons.Default.AutoAwesome)
                    )
                    items.forEach { item ->
                        VinylNavigationBarItem(
                            item = item,
                            isSelected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Collection.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable(Screen.Collection.route) {
                MyCollectionScreen(
                    onRecordClick = { recordId ->
                        navController.navigate(Screen.Details.createRoute(recordId))
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchReleaseScreen(
                    onRecordClick = { recordId ->
                        navController.navigate(Screen.Details.createRoute(recordId))
                    }
                )
            }
            composable(Screen.Wishlist.route) {
                WishlistScreen(
                    onRecordClick = { recordId ->
                        navController.navigate(Screen.Details.createRoute(recordId))
                    }
                )
            }
            composable(Screen.Details.route) { backStackEntry ->
                val recordId = backStackEntry.arguments?.getString("recordId") ?: ""
                RecordDetailsScreen(
                    recordId = recordId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
