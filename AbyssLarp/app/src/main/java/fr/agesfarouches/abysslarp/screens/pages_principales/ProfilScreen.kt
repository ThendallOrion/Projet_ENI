package fr.agesfarouches.abysslarp.screens.pages_principales

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.agesfarouches.abysslarp.SessionManager
import fr.agesfarouches.abysslarp.navigation.Routes
import fr.agesfarouches.abysslarp.screens.menu.TopBarMenu
import fr.agesfarouches.abysslarp.viewmodels.pages_principales.LoginState
import kotlinx.coroutines.launch

@Composable
fun ProfilScreen(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val scope = rememberCoroutineScope()

    val pseudo by sessionManager.pseudoFlow.collectAsState(initial = null)
    //val email by sessionManager.emailFlow.collectAsState(initial = null)
    val role by sessionManager.roleFlow.collectAsState(initial = null)



    Scaffold(
        topBar = { TopBarMenu(titre = "Mon compte", navController = navController) }
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
            //Text("Email : ${email ?: "-"}", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(8.dp))
            Text("Rôle : ${role ?: "-"}", style = MaterialTheme.typography.bodyLarge)

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    scope.launch {
                        sessionManager.clearSession()
                        navController.navigate(Routes.LOGIN_MENU) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Se déconnecter")
            }
        }
    }
}