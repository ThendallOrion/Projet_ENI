package fr.agesfarouches.abysslarp.screens.pages_principales

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import fr.agesfarouches.abysslarp.viewmodels.pages_principales.GnListUiState
import fr.agesfarouches.abysslarp.viewmodels.pages_principales.GnListViewModel
import fr.agesfarouches.abysslarp.api.GnListItem
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun GnListScreen(
    navController: NavController,
    viewModel: GnListViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val currentState = state) {
            is GnListUiState.Loading -> CircularProgressIndicator()

            is GnListUiState.Error -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Erreur : ${currentState.message}")
                Spacer(Modifier.height(12.dp))
                Button(onClick = { viewModel.fetchGnList() }) {
                    Text("Réessayer")
                }
            }

            is GnListUiState.Success -> {
                var filter by remember {
                    mutableStateOf(DateFilter.ALL)
                }
                val today = LocalDate.now()
                val filteredList = currentState.gnList.filter { gn ->
                    val dateGn = LocalDate.parse(
                        gn.dateDebut.substring(0, 10)
                    )
                    when(filter) {
                        DateFilter.ALL -> true
                        DateFilter.FUTURE ->
                            !dateGn.isBefore(today)
                        DateFilter.PAST ->
                            dateGn.isBefore(today)
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FilterButtons(
                        selected = filter,
                        onSelected = {
                            filter = it
                        }
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredList) { gn ->
                            AffichageList(
                                gn = gn,
                                onClick = {
                                    navController.navigate(
                                        "gn_detail/${gn.id}"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AffichageList(
    gn: GnListItem,
    onClick: () -> Unit
) {
    Spacer(Modifier.height(8.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = gn.image,
                    contentDescription = gn.nom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                DateBadge(
                    dateDebut = gn.dateDebut,
                    dateFin = gn.dateFin,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 12.dp, y = (-8).dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = gn.nom,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(4.dp))

                gn.description?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Ages Farouches",
                    fontStyle = FontStyle.Italic,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
private fun DateBadge(dateDebut: String,dateFin: String, modifier: Modifier = Modifier) {
    val parsed = LocalDate.parse(dateDebut.substring(0, 10))
    val dayD = parsed?.dayOfMonth?.toString() ?: "?"

    val parsed2 = LocalDate.parse(dateFin.substring(0, 10))
    val dayF = parsed2?.dayOfMonth?.toString() ?: "?"

    val month = parsed
        ?.month
        ?.getDisplayName(TextStyle.SHORT, Locale.FRENCH)
        ?.uppercase(Locale.FRENCH)
        ?: ""

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = month, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Red)
        Text(text = dayD +"/"+ dayF, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

enum class DateFilter {
    ALL,
    FUTURE,
    PAST
}

@Composable
fun FilterButtons(
    selected: DateFilter,
    onSelected: (DateFilter) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {


        FilterChip(
            selected = selected == DateFilter.ALL,
            onClick = {
                onSelected(DateFilter.ALL)
            },
            label = {
                Text("Tous")
            }
        )


        FilterChip(
            selected = selected == DateFilter.FUTURE,
            onClick = {
                onSelected(DateFilter.FUTURE)
            },
            label = {
                Text("À venir")
            }
        )


        FilterChip(
            selected = selected == DateFilter.PAST,
            onClick = {
                onSelected(DateFilter.PAST)
            },
            label = {
                Text("Terminés")
            }
        )

    }

}
