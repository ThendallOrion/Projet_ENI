package fr.agesfarouches.abysslarp.screens.jeux

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//test color
val Bleu_color = Color(0xFF5EC8FF)
val Black_color = Color(0xFF061320)
val Background = Color(0xFF061320)
val Card = Color(0xFF0B2238)
val Border = Color(0xFF1C7ED6)
val Cyan = Color(0xFF38BDF8)
val Blue = Color(0xFF1D4ED8)
val Blue2 = Color(0xFF2C9FFF)
val Green = Color(0xFF22C55E)
val Orange = Color(0xFFF59E0B)
val Red = Color(0xFFEF4444)
val Purple = Color(0xFFA855F7)
val White = Color(0xFFE8F1FF)

val padding_dist = 8.dp

@Composable
fun PowerScreen(
    onBack: () -> Unit = {},
    onGnClick: (Int) -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF061320))
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding_dist)
            ) {
                //menu droite
                RightPanel(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxHeight(),
                    Text="droite"
                )

                Spacer(Modifier.width(padding_dist))
                //menu centrale
                CenterPanel(
                    modifier = Modifier
                        .weight(0.50f)
                        .fillMaxHeight()
                )

                Spacer(Modifier.width(padding_dist))
                //menu gauche
                RightPanel(
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxHeight(),
                            Text="gauche"
                )
            }
        }
    }
}

@Composable
fun CenterPanel(
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier,
        color = Color(0xFF091D30),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            color = Blue2
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                "CENTRE",
                color = Color.White,
                fontSize = 30.sp
            )
        }
    }
}

@Composable
fun RightPanel(
    modifier: Modifier = Modifier,
    Text: String
) {

    Surface(
        modifier = modifier,
        color = Color(0xFF091D30),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            color = Blue2
        )
    ) {

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                Text,
                color = Color.White
            )
        }
    }
}
