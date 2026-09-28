package fr.agesfarouches.abysslarp.screens.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import fr.agesfarouches.abysslarp.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMenu(
    titre: String,
    showBack: Boolean = true,
    onBack: (() -> Unit)? = null,
    onNavigateToProfil: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val pseudo by sessionManager.pseudoFlow.collectAsState(initial = null)

    CenterAlignedTopAppBar(
        title = { Text(titre) },
        navigationIcon = {
            if (showBack && onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour"
                    )
                }
            }
        },
        actions = {
            if (onNavigateToProfil != null) {
                Button(onClick = onNavigateToProfil) {
                    Text(pseudo?.let { "👤 $it" } ?: "Non connecté")
                }
            }
        }
    )
}
