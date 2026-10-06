package fr.agesfarouches.abysslarp.screens.jeux

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.agesfarouches.abysslarp.ui.theme.AbyssColors

//couleur des noeuds selon le niveau de charge
private fun couleurNiveau(pourcentage: Int): Color = when {
    pourcentage <= 0 -> AbyssColors.PisteNul
    pourcentage < 30 -> AbyssColors.NiveauCritique
    pourcentage < 50 -> AbyssColors.NiveauFaible
    pourcentage < 100 -> AbyssColors.NiveauBon
    else -> AbyssColors.NiveauPlein
}

// Couleur du générateur selon la charge
private fun couleurGenerateur(utilise: Int, production: Int): Color = when {
    utilise <= production - 2 -> AbyssColors.GenerateurProgression
    utilise <= production -> AbyssColors.GenerateurOptimal
    utilise <= production + 2 -> AbyssColors.GenerateurSurcharge
    else -> AbyssColors.GenerateurCritique
}

@Composable
internal fun LigneReglage(
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

@Composable
fun GeneratorCard(
    utilise: Int,
    production: Int,
    modifier: Modifier = Modifier
) {
    val fraction = if (production > 0) (utilise / production.toFloat()).coerceIn(0f, 1f) else 0f
    // tween permet de modifier la vitesse de l'annimation en ms
    // 300 de base
    val fractionAnimee by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 150),
        label = "remplissageGenerateur"
    )
    val couleurAnneau by animateColorAsState(
        targetValue = couleurGenerateur(utilise, production),
        animationSpec = tween(durationMillis = 150),
        label = "couleurGenerateur"
    )

    Box(
        modifier = modifier
            .size(130.dp)
            .background(AbyssColors.Fond_Panneau, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Anneau : piste complète + arc de progression (sens horaire, départ en haut)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val epaisseur = 10.dp.toPx()
            val decalage = Offset(epaisseur / 2, epaisseur / 2)
            val taille = Size(size.width - epaisseur, size.height - epaisseur)

            drawArc(
                color = AbyssColors.PisteNul,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = decalage,
                size = taille,
                style = Stroke(width = epaisseur)
            )
            if (fractionAnimee > 0f) {
                drawArc(
                    color = couleurAnneau,
                    startAngle = -90f,
                    sweepAngle = 360f * fractionAnimee,
                    useCenter = false,
                    topLeft = decalage,
                    size = taille,
                    style = Stroke(width = epaisseur, cap = StrokeCap.Round)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row() {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = ZoneVaisseau.GENERATEUR.label,
                    tint = Color.Yellow,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = ZoneVaisseau.GENERATEUR.label.uppercase(),
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
            Text(
                text = "$utilise / $production",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
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
                trackColor = AbyssColors.PisteNul,
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