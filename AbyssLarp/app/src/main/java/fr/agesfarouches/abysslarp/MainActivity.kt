package fr.agesfarouches.abysslarp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import fr.agesfarouches.abysslarp.navigation.AppNavigation
import fr.agesfarouches.abysslarp.screens.NFC.NfcController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NfcController.init(this)
        setContent {
            AppNavigation()
        }
    }
}

