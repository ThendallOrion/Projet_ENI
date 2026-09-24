package fr.agesfarouches.abysslarp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import fr.agesfarouches.abysslarp.screens.nfc.NfcMenu

import fr.agesfarouches.abysslarp.screens.pages_principales.GnDetailScreen
import fr.agesfarouches.abysslarp.screens.pages_principales.GnListScreen
import fr.agesfarouches.abysslarp.screens.pages_principales.HomeScreen
import fr.agesfarouches.abysslarp.screens.pages_principales.HomeScreen_Test
import fr.agesfarouches.abysslarp.screens.pages_principales.LoginScreen
import fr.agesfarouches.abysslarp.screens.pages_principales.ProfilScreen
import fr.agesfarouches.abysslarp.screens.pages_principales.TestNewPage

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME_TEST
    ) {
        composable(Routes.HOME_TEST) {
            HomeScreen_Test(
                onNavigateToHOME = { navController.navigate(Routes.HOME) },
                onNavigateToGN = { navController.navigate(Routes.GN_LIST) },
                onNavigateToNfc = { navController.navigate(Routes.NFC_MENU) },
                onNavigateToLogin = { navController.navigate(Routes.LOGIN_MENU) },
                //rappel new page
                onNavigateToNewPage = { navController.navigate(Routes.NEW_PAGE) }
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
        composable(Routes.NFC_MENU) {
            NfcMenu(navController = navController)
        }

        composable(Routes.LOGIN_MENU) {
            LoginScreen(navController = navController)
        }

        composable(Routes.HOME) {

            HomeScreen(
                onNavigateToGN = { navController.navigate(Routes.GN_LIST) },
                onNavigateToNfc = { navController.navigate(Routes.NFC_MENU) }
            )
        }
        composable(Routes.PROFIL) {
            ProfilScreen(navController = navController)
        }
        //rappel new page
        composable(Routes.NEW_PAGE) {
            TestNewPage(navController = navController)
        }
    }
}