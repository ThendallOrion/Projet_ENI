package fr.agesfarouches.abysslarp.screens.jeux

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ConstraintSetScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.agesfarouches.abysslarp.ui.theme.AbyssColors
import fr.agesfarouches.abysslarp.ui.theme.DimmensionMenu
import fr.agesfarouches.abysslarp.ui.theme.PaddingEcran
import fr.agesfarouches.abysslarp.viewmodels.jeux.EtatEnergie
import fr.agesfarouches.abysslarp.viewmodels.jeux.PowerViewModel



private val MargeBord = 20.dp

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
                top.linkTo(previousRef.bottom, PaddingEcran)
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
                .weight(DimmensionMenu.Panneau_Lateral)
                .fillMaxHeight()
        )

        Spacer(Modifier.width(PaddingEcran))

        CenterPanel(
            etat = etat,
            modifier = Modifier
                .weight(DimmensionMenu.Panneau_Central)
                .fillMaxHeight()
        )

        Spacer(Modifier.width(PaddingEcran))

        //Panneau Gauche
        PanneauEnergie(
            etat = etat,
            onAjouter = viewModel::ajouter,
            onRetirer = viewModel::retirer,
            modifier = Modifier
                .weight(DimmensionMenu.Panneau_Lateral)
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


