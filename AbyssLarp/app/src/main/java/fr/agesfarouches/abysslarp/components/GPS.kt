package fr.agesfarouches.abysslarp.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
@Composable
fun BoutonGps(adresse: String) {

    val context = LocalContext.current
    Button(
        onClick = {
            val uri = Uri.parse(
                "google.navigation:q=${Uri.encode(adresse)}"
            )
            val intent = Intent(
                Intent.ACTION_VIEW,
                uri
            )

            try {
                context.startActivity(intent)
            } catch(e: Exception) {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://www.google.com/maps/search/?api=1&query=${Uri.encode(adresse)}"
                    )
                )
                context.startActivity(webIntent)
            }
        }
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "GPS"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Itinéraire")
    }
}