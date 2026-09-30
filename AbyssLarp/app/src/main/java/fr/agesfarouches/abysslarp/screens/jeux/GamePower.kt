package fr.agesfarouches.abysslarp.screens.jeux

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ConstraintSetScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.agesfarouches.abysslarp.viewmodels.jeux.EtatEnergie
import fr.agesfarouches.abysslarp.viewmodels.jeux.PowerViewModel

// Couleurs et dimensions

private object AbyssColors {
    val Fond_Ecran = Color.Black
    val Fond_Panneau = Color(0xFF091D30)
    val Bordure = Color(0xFF2C9FFF)
    val NoeudFond = Color(0xFF1457FF)

    // Niveaux de charge d'un EnergyNode
    val NiveauNul = Color(0xFF6B7280)       // 0 %
    val NiveauCritique = Color(0xFFEF4444)  // < 30 %
    val NiveauFaible = Color(0xFFF59E0B)    // < 50 %
    val NiveauBon = Color(0xFF22C55E)       // < 100 %
    val NiveauPlein = Color(0xFF2C9FFF)     // 100 %
}

private fun couleurNiveau(pourcentage: Int): Color = when {
    pourcentage <= 0 -> AbyssColors.NiveauNul
    pourcentage < 30 -> AbyssColors.NiveauCritique
    pourcentage < 50 -> AbyssColors.NiveauFaible
    pourcentage < 100 -> AbyssColors.NiveauBon
    else -> AbyssColors.NiveauPlein
}

private val PaddingEcran = 8.dp
private val MargeBord = 20.dp
private val EspaceEntreNoeuds = 8.dp

// Zones du vaisseau : source unique pour les ids, libellés

const val PRODUCTION_GENERATEUR = 10

enum class ZoneVaisseau(
    val label: String,
    val capaciteMax: Int = 0 // unités max que le nœud peut recevoir
) {
    GENERATEUR("Générateur"),
    FTL("Moteur FTL", capaciteMax = 4),
    SURVIE("Survie", capaciteMax = 3),
    LABO("Laboratoires", capaciteMax = 3),
    ARMEMENT("Armement", capaciteMax = 5),
    BATTERIE("Batterie", capaciteMax = 4)
}

// Nœuds empilés en colonne à droite (de haut en bas)
private val ZonesEnergie = listOf(
    ZoneVaisseau.SURVIE,
    ZoneVaisseau.FTL,
    ZoneVaisseau.LABO,
    ZoneVaisseau.ARMEMENT,
    ZoneVaisseau.BATTERIE
)

private fun ConstraintSetScope.ref(zone: ZoneVaisseau) = createRefFor(zone)

private val ContraintesCentre = ConstraintSet {
    constrain(ref(ZoneVaisseau.GENERATEUR)) {
        top.linkTo(parent.top, 10.dp)
        start.linkTo(parent.start, 10.dp)
    }

    ZonesEnergie.forEachIndexed { index, zone ->
        val previousRef = if (index > 0) ref(ZonesEnergie[index - 1]) else null
        constrain( ref(zone)) {
            if (previousRef == null) {
                top.linkTo(parent.top, PaddingEcran)
            } else {
                top.linkTo(previousRef.bottom, EspaceEntreNoeuds)
            }
            end.linkTo(parent.end, MargeBord)
        }
    }
}

// Écran

@Composable
fun PowerScreen(
    viewModel: PowerViewModel = viewModel()
) {
    // partagé entre le panneau de droite et le centre
    val etat by viewModel.etat.collectAsStateWithLifecycle()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(AbyssColors.Fond_Ecran)
            .padding(PaddingEcran)
    ) {
        //Panneau Gauche
        PanneauLateral(
            titre = "Énergie",
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight()
        )

        Spacer(Modifier.width(PaddingEcran))

        CenterPanel(
            etat = etat,
            modifier = Modifier
                .weight(0.70f)
                .fillMaxHeight()
        )

        Spacer(Modifier.width(PaddingEcran))

        //Panneau Gauche
        PanneauEnergie(
            etat = etat,
            onAjouter = viewModel::ajouter,
            onRetirer = viewModel::retirer,
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight()
        )
    }
}

@Composable
fun CenterPanel(
    etat: EtatEnergie,
    modifier: Modifier = Modifier
) {
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
            GeneratorCard(
                utilise = etat.totalUtilise,
                production = etat.production,
                modifier = Modifier.layoutId(ZoneVaisseau.GENERATEUR)
            )

            ZonesEnergie.forEach { zone ->
                EnergyNode(
                    title = zone.label,
                    valeur = etat.allouee(zone),
                    capaciteMax = zone.capaciteMax,
                    modifier = Modifier.layoutId(zone)
                )
            }
        }
    }
}

// Panneaux latéraux

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

@Composable
fun PanneauEnergie(
    etat: EtatEnergie,
    onAjouter: (ZoneVaisseau) -> Unit,
    onRetirer: (ZoneVaisseau) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = AbyssColors.Fond_Panneau,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, AbyssColors.Bordure)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ZonesEnergie.forEach { zone ->
                LigneReglage(
                    zone = zone,
                    valeur = etat.allouee(zone),
                    peutRetirer = etat.peutRetirer(zone),
                    peutAjouter = etat.peutAjouter(zone),
                    onRetirer = { onRetirer(zone) },
                    onAjouter = { onAjouter(zone) }
                )
            }
        }
    }
}

@Composable
private fun LigneReglage(
    zone: ZoneVaisseau,
    valeur: Int,
    peutRetirer: Boolean,
    peutAjouter: Boolean,
    onRetirer: () -> Unit,
    onAjouter: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = zone.label,
            color = Color.White,
            fontSize = 12.sp
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BoutonUnite(
                icone = Icons.Default.Remove,
                description = "Retirer une unité à ${zone.label}",
                enabled = peutRetirer,
                onClick = onRetirer
            )
            Text(text = valeur.toString(), color = Color.White)
            BoutonUnite(
                icone = Icons.Default.Add,
                description = "Ajouter une unité à ${zone.label}",
                enabled = peutAjouter,
                onClick = onAjouter
            )
        }
    }
}

@Composable
private fun BoutonUnite(
    icone: androidx.compose.ui.graphics.vector.ImageVector,
    description: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.size(32.dp)
    ) {
        Icon(
            imageVector = icone,
            contentDescription = description,
            tint = if (enabled) Color.White else Color.White.copy(alpha = 0.3f)
        )
    }
}

// ---------------------------------------------------------------------------
// Composants du panneau central
// ---------------------------------------------------------------------------
/*
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
            tint = Color.Yellow,
            modifier = Modifier.size(48.dp)
        )
    }
}
*/
@Composable
fun GeneratorCard(
    utilise: Int,
    production: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(150.dp, 100.dp),
        colors = CardDefaults.cardColors(containerColor = AbyssColors.Fond_Panneau),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, AbyssColors.Bordure)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row() {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = ZoneVaisseau.GENERATEUR.label,
                    tint = Color.Yellow,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = ZoneVaisseau.GENERATEUR.label.uppercase(), color = Color.White)
            }

            Text(text = "$utilise / $production unités", color = Color.White)
        }
    }
}

@Composable
fun EnergyNode(
    title: String,
    valeur: Int,
    capaciteMax: Int,
    modifier: Modifier = Modifier
) {
    val pourcentage = if (capaciteMax > 0) valeur * 100 / capaciteMax else 0
    val couleurBarre = couleurNiveau(pourcentage)

    Card(
        modifier = modifier.width(150.dp),
        colors = CardDefaults.cardColors(containerColor = AbyssColors.Fond_Panneau),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, AbyssColors.Bordure)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = title, color = Color.White)
            Text(text = "$pourcentage %", color = couleurBarre, fontSize = 12.sp)
            LinearProgressIndicator(
                progress = { pourcentage / 100f },
                color = couleurBarre,
                trackColor = AbyssColors.NiveauNul,
                //supprimer le point dessiné à la fin de la ligne
                drawStopIndicator = {},
                //pas d'ecart entre les lignes de couleur
                gapSize = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}