package fr.agesfarouches.abysslarp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*

import fr.agesfarouches.abysslarp.screens.*
import fr.agesfarouches.abysslarp.screens.menu_principal.HomeScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToGN = {
                    navController.navigate(
                        Routes.GN_LIST
                    )
                }
            )
        }
        composable(Routes.GN_LIST) {
            GnListScreen(navController = navController)
        }
        composable(Routes.GN_DETAIL) {
            backStackEntry ->
            val id = backStackEntry.arguments
                ?.getString("id")
            GnDetailScreen(
                navController = navController,
                gnId = id?.toInt() ?: 0
            )
        }
    }
}