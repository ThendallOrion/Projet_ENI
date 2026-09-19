package fr.agesfarouches.abysslarp.screens.NFC

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.agesfarouches.abysslarp.VM.NfcLookupState
import fr.agesfarouches.abysslarp.VM.NfcViewModel

@Composable
fun NfcMenu(navController: NavController, viewModel: NfcViewModel = viewModel()) {
    val context = LocalContext.current
    val activity = context as? Activity

    val tagId by NfcController.lastTagId.collectAsState()
    val lookupState by viewModel.state.collectAsState()

    DisposableEffect(Unit) {
        activity?.let { NfcController.startListening(it) }
        onDispose {
            activity?.let { NfcController.stopListening(it) }
            NfcController.clear()
            viewModel.reset()
        }
    }

    // Dès qu'un tag est lu (ou simulé), on interroge le serveur
    LaunchedEffect(tagId) {
        tagId?.let { viewModel.lookup(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
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
            Text("UID : $tagId")
            Spacer(Modifier.height(16.dp))

            when (val s = lookupState) {
                is NfcLookupState.Loading -> CircularProgressIndicator()

                is NfcLookupState.Found -> {
                    val r = s.result
                    when (r.type) {
                        "CHARACTER" -> Text("Personnage : ${r.nomCharacter} (${r.age} ans)")
                        "STATION" -> Text("Station : ${r.nomCharacter}")
                        else -> Text("Type inconnu : ${r.type}")
                    }
                }

                is NfcLookupState.Unknown -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Cette carte n'est associée à rien pour le moment.")
                    // TODO : UI d'association (choix entityType + entityId)
                }

                is NfcLookupState.Error -> Text("Erreur : ${s.message}")

                NfcLookupState.Idle -> {}
            }
        }

        Spacer(Modifier.height(32.dp))
        OutlinedButton(onClick = { NfcController.simulateScan() }) {
            Text("Simuler un scan (émulateur)")
        }
    }
}