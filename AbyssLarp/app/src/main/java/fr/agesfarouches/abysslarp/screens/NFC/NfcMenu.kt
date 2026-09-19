package fr.agesfarouches.abysslarp.screens.NFC

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NfcMenu(navController: NavController) {
    val context = LocalContext.current
    val activity = context as? Activity

    val tagId by NfcController.lastTagId.collectAsState()

    // Démarre l'écoute NFC à l'entrée sur l'écran, l'arrête à la sortie
    DisposableEffect(Unit) {
        activity?.let { NfcController.startListening(it) }
        onDispose {
            activity?.let { NfcController.stopListening(it) }
            NfcController.clear()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Scan NFC", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        if (tagId == null) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text("Approchez une carte du téléphone…")
        } else {
            Text(
                text = "GUID détecté :",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = tagId!!,
                style = MaterialTheme.typography.headlineSmall
            )
            // TODO: appel API pour chercher si ce GUID est déjà associé à un élément
        }

        Spacer(Modifier.height(32.dp))

        // Utile uniquement sur émulateur, qui n'a pas de puce NFC
        OutlinedButton(onClick = { NfcController.simulateScan() }) {
            Text("Simuler un scan (émulateur)")
        }
    }
}