package fr.agesfarouches.abysslarp.screens.menu_principal


import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.core.net.toUri
import fr.agesfarouches.abysslarp.viewmodels.GnDetailState
import fr.agesfarouches.abysslarp.viewmodels.GnDetailViewModel
import fr.agesfarouches.abysslarp.utils.BoutonGps
import fr.agesfarouches.abysslarp.utils.formatDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GnDetailScreen(
    navController: NavController,
    gnId: Int,
    viewModel: GnDetailViewModel = viewModel())
 {
    LaunchedEffect(gnId) {
        viewModel.loadGnDetail(gnId)
    }
    val state = viewModel.state
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Détail du GN")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when(val currentState = state){
            is GnDetailState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
            is GnDetailState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = currentState.message,
                        color = Color.Red
                    )
                }
            }
            is GnDetailState.Success -> {
                val gn = currentState.gn

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(16.dp)

                ){
                    AsyncImage(
                        model = gn.image,
                        contentDescription = gn.nom,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Text(
                        text = gn.nom,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    InfoText(
                        titre = "Date",
                        texte = "Du : " + formatDate(gn.dateDebut) + " au : " + formatDate(gn.dateFin)                   )

                    InfoText(
                        titre = "Adresse",
                        texte = gn.lieu
                    )

                    BoutonGps(
                        gn.lieu
                    )

                    InfoText(
                        titre = "Équipe organisatrice",
                        texte = gn.equipeOrga
                    )

                    Text(
                        text = "Site web",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Button(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                gn.siteWeb.toUri()
                            )
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                        //containerColor = Color.Blue
                        )
                    ) {
                        Text(
                            text = "🌐"+ gn.siteWeb
                        )
                    }

                    InfoText(
                        titre = "Description",
                        texte = gn.description
                    )

                    InfoText(
                        titre = "Ambiance",
                        texte = gn.ambiance
                    )

                    InfoText(
                        titre = "Liens utiles",
                        texte = gn.liensUtiles
                    )

                    InfoText(
                        titre = "Prix",
                        texte = "PJ : ${gn.prixPJ} €   •   PNJ : ${gn.prixPNJ} €"
                    )
                }
            }
        }
    }
}

@Composable
fun InfoText(
    titre:String,
    texte:String
){
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ){
        Text(
            text = titre,
            fontWeight = FontWeight.Bold
        )
        Text(texte)
    }
}