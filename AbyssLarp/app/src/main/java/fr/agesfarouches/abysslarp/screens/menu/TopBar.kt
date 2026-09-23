package fr.agesfarouches.abysslarp.screens.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMenu(pseudo: String) {
    TopAppBar(
    title = { Text("$pseudo") },
    navigationIcon = {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Profile"
        )
    },
    actions = {
        IconButton(onClick = {
            // TODO: naviguer vers les options
        }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
        }
    },
    colors = TopAppBarDefaults.topAppBarColors()
    )

}