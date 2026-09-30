package fr.agesfarouches.abysslarp.screens.jeux

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ConstraintSetScope

// Couleurs et dimensions

private object AbyssColors {
    val Fond_Ecran = Color.Black
    val Fond_Panneau = Color(0xFF091D30)
    val Bordure = Color(0xFF2C9FFF)
    val NoeudFond = Color(0xFF1457FF)
}

private val PaddingEcran = 8.dp
private val MargeBord = 20.dp
private val EspaceEntreNoeuds = 18.dp

// Zones du vaisseau : source unique pour les ids, libellés

enum class ZoneVaisseau(val label: String) {
    GENERATEUR("Générateur"),
    NOEUD_ELECTRIQUE("Nœud électrique"),
    FTL("Poste FTL"),
    HABITAT("Habitations"),
    LABO("Laboratoires"),
    DEFENSE("Défenses"),
    BATTERIE("Batterie")
}

// Nœuds empilés en colonne à droite
private val ZonesEnergie = listOf(
    ZoneVaisseau.FTL,
    ZoneVaisseau.HABITAT,
    ZoneVaisseau.LABO,
    ZoneVaisseau.DEFENSE,
    ZoneVaisseau.BATTERIE
)

private fun ConstraintSetScope.ref(zone: ZoneVaisseau) = createRefFor(zone)

private val ContraintesCentre = ConstraintSet {
    constrain(ref(ZoneVaisseau.GENERATEUR)) {
        top.linkTo(parent.top, 10.dp)
        start.linkTo(parent.start, 10.dp)
    }

    constrain(ref(ZoneVaisseau.NOEUD_ELECTRIQUE)) {
        centerTo(parent)
    }

    ZonesEnergie.forEachIndexed { index, zone ->
        val previousRef = if (index > 0) ref(ZonesEnergie[index - 1]) else null
        constrain(ref(zone)) {
            if (previousRef == null) {
                top.linkTo(parent.top, 40.dp)
            } else {
                top.linkTo(previousRef.bottom, EspaceEntreNoeuds)
            }
            end.linkTo(parent.end, MargeBord)
        }
    }
}


// Écran


@Composable
fun PowerScreen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(AbyssColors.Fond_Ecran)
            .padding(PaddingEcran)
    ) {
        PanneauLateral(
            titre = "Énergie",
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight()
        )

        Spacer(Modifier.width(PaddingEcran))

        CenterPanel(
            modifier = Modifier
                .weight(0.70f)
                .fillMaxHeight()
        )

        Spacer(Modifier.width(PaddingEcran))

        PanneauLateral(
            titre = "droite",
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight()
        )
    }
}

@Composable
fun CenterPanel(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = AbyssColors.Fond_Panneau,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, AbyssColors.Bordure)
    ) {
        ConstraintLayout(
            constraintSet = ContraintesCentre,
            modifier = Modifier.fillMaxSize()
        ) {
            GeneratorCard(Modifier.layoutId(ZoneVaisseau.GENERATEUR))

            NoeudElectrique(Modifier.layoutId(ZoneVaisseau.NOEUD_ELECTRIQUE))

            ZonesEnergie.forEach { zone ->
                EnergyNode(
                    title = zone.label,
                    modifier = Modifier.layoutId(zone)
                )
            }
        }
    }
}

@Composable
fun PanneauLateral(
    titre: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = AbyssColors.Fond_Panneau,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, AbyssColors.Bordure)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = titre, color = Color.White)
        }
    }
}

//éléments centraux

@Composable
fun NoeudElectrique(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(120.dp)
            .background(AbyssColors.NoeudFond, CircleShape)
            .border(3.dp, Color.Cyan, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = ZoneVaisseau.NOEUD_ELECTRIQUE.label,
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun GeneratorCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.size(150.dp, 100.dp),
        colors = CardDefaults.cardColors(containerColor = AbyssColors.Fond_Panneau),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, AbyssColors.Bordure)
    ) {
        Text(
            text = ZoneVaisseau.GENERATEUR.label.uppercase(),
            modifier = Modifier.padding(16.dp),
            color = Color.White
        )
    }
}

@Composable
fun EnergyNode(title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(150.dp),
        colors = CardDefaults.cardColors(containerColor = AbyssColors.Fond_Panneau),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, AbyssColors.Bordure)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
    }
}