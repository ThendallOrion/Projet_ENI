package fr.agesfarouches.abysslarp.screens.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.agesfarouches.abysslarp.SessionManager
import fr.agesfarouches.abysslarp.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMenu(
    titre : String,
    navController: NavController? = null,
    onNavigateToPROFIL: () -> Unit = { navController?.navigate(Routes.PROFIL) }
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val pseudo by sessionManager.pseudoFlow.collectAsState(initial = null)

    TopAppBar(
        title = { Text(titre) },
        navigationIcon = {
            navController?.let {
                IconButton(onClick = { it.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour"
                    )
                }
            }
        },
        actions = {
            Button(onClick = onNavigateToPROFIL) { Text(pseudo?.let { "👤 $it" } ?: "Non connecté") }
        }
    )
}
