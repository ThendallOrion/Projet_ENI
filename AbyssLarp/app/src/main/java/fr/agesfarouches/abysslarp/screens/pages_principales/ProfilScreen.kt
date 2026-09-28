package fr.agesfarouches.abysslarp.screens.pages_principales

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fr.agesfarouches.abysslarp.SessionManager
import fr.agesfarouches.abysslarp.screens.menu.TopBarMenu
import kotlinx.coroutines.launch

@Composable
fun ProfilScreen(
    onBack: () -> Unit = {},
    onNavigateToProfil: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()

    val pseudo by sessionManager.pseudoFlow.collectAsState(initial = null)
    val role by sessionManager.roleFlow.collectAsState(initial = null)

    var pseudo_temp by remember(pseudo) { mutableStateOf(pseudo ?: "") }

    Scaffold(
        topBar = {
            TopBarMenu(
                titre = "Mon compte",
                onBack = onBack,
                onNavigateToProfil = onNavigateToProfil
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Pseudo : ${pseudo ?: "-"}", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = pseudo_temp,
                onValueChange = { pseudo_temp = it },
                label = { Text("Pseudo") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text("Rôle : ${role ?: "-"}", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    scope.launch {
                        sessionManager.clearSession()
                        onLogout()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Se déconnecter")
            }
        }
    }
}
