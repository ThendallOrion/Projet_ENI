package fr.agesfarouches.abysslarp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import fr.agesfarouches.abysslarp.screens.jeux.PowerScreen
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
    val backStack = rememberNavBackStack(HomeTestKey)

    fun goTo(key: NavKey) {
        backStack.add(key)
    }

    fun goBack() {
        if (backStack.size > 1) backStack.removeLastOrNull()
    }

    fun resetTo(key: NavKey) {
        backStack.clear()
        backStack.add(key)
    }

    NavDisplay(
        backStack = backStack,
        onBack = { goBack() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {

            // ---- Écrans de dev -------------------------------------------
            entry<HomeTestKey> {
                HomeScreen_Test(
                    onNavigateToHOME = { goTo(HomeKey) },
                    onNavigateToGN = { goTo(GnListKey) },
                    onNavigateToNfc = { goTo(NfcKey) },
                    onNavigateToLogin = { goTo(LoginKey) },
                    onNavigateToNewPage = { goTo(NewPageKey) }
                )
            }
            entry<NewPageKey> {
                TestNewPage()
            }

            // ---- Authentification ----------------------------------------
            entry<LoginKey> {
                LoginScreen(
                    // Succès du login : on vide la pile et on part sur Home.
                    onLoginSuccess = { resetTo(HomeKey) }
                )
            }

            // ---- Pages principales ---------------------------------------
            entry<HomeKey> {
                HomeScreen(
                    onNavigateToGN = { goTo(GnListKey) },
                    onNavigateToNfc = { goTo(NfcKey) },
                    onNavigateToProfil = { goTo(ProfilKey) }
                    // Pas de bouton retour sur Home : voir TopBarMenu(showBack = false)
                )
            }
            entry<ProfilKey> {
                ProfilScreen(
                    onBack = { goBack() },
                    onNavigateToProfil = { /* déjà sur le profil */ },
                    // Déconnexion : plus de retour possible vers les écrans connectés.
                    onLogout = { resetTo(LoginKey) }
                )
            }
            entry<NfcKey> {
                // NfcMenu n'a pas de barre du haut : le retour se fait avec
                // le bouton/geste Android, géré par NavDisplay (onBack).
                NfcMenu()
            }

            // ---- GN -------------------------------------------------------
            entry<GnListKey> {
                GnListScreen(
                    onBack = { goBack() },
                    onNavigateToProfil = { goTo(ProfilKey) },
                    // On fabrique la clé AVEC le paramètre : c'est typé,
                    // pas de string, pas de risque de faute de frappe.
                    onGnClick = { gnId -> goTo(GnDetailKey(gnId)) }
                )
            }

            // Ici `key` est la clé qui a ouvert l'écran : on lit key.id
            // directement, sans navArgument ni getInt("id").
            entry<GnDetailKey> { key ->
                GnDetailScreen(
                    gnId = key.id,
                    onBack = { goBack() },
                    onNavigateToProfil = { goTo(ProfilKey) }
                )
            }

            // ---- Jeux -----------------------------------------------------
            // Cette entrée manquait dans l'ancien NavHost (GAME_POWER était orphelin).
            entry<GamePowerKey> {
                PowerScreen()
            }
        }
    )
}
