package fr.agesfarouches.abysslarp.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//code Couleurs
internal object AbyssColors {
    val Fond_Ecran = Color.Black
    val Fond_Panneau = Color(0xFF091D30)
    val Bordure = Color(0xFF2C9FFF)
    val NoeudFond = Color(0xFF1457FF)

    // Niveaux de charge d'un EnergyNode
    val PisteNul = Color(0xFF1C3A57)        // 0 %
    val NiveauCritique = Color(0xFFEF4444)  // < 30 %
    val NiveauFaible = Color(0xFFF59E0B)    // < 50 %
    val NiveauBon = Color(0xFF22C55E)       // < 100 %
    val NiveauPlein = Color(0xFF2C9FFF)     // 100 %

    // Générateur circulaire
    // Générateur circulaire
    val GenerateurProgression = NiveauPlein     //  production - 2 ou moins
    val GenerateurOptimal = NiveauBon           //  production - 1 à production
    val GenerateurSurcharge = NiveauFaible      //  surcharge de 1 à 2
    val GenerateurCritique = NiveauCritique     //  surcharge de 3 à 4
}

internal object DimmensionMenu {
val Panneau_Lateral = 0.20f
val Panneau_Central = 0.60f
}

internal val PaddingEcran = 8.dp