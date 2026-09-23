package fr.agesfarouches.abysslarp.screens.pages_principales

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.agesfarouches.abysslarp.screens.menu.TopBarMenu


@Composable
fun HomeScreen(
    onNavigateToGN: () -> Unit,
    onNavigateToNfc: () -> Unit,
    pseudo: String,
    //rappel new page
    onNavigateToNewPage: () -> Unit

) {

    TopBarMenu(pseudo)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Abyss Larp", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(40.dp))
        Button(onClick = onNavigateToGN) { Text("Gestion des GN") }
        Spacer(Modifier.height(12.dp))
        Button(onClick = onNavigateToNfc) { Text("Scanner une carte NFC") }
        Spacer(Modifier.height(12.dp))
        //rappel new page
        Spacer(Modifier.height(12.dp))
        Button(onClick = onNavigateToNewPage) { Text("NewPage") }
    }
}