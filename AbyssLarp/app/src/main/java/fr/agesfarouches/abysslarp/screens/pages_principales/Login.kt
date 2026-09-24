package fr.agesfarouches.abysslarp.screens.pages_principales

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.agesfarouches.abysslarp.navigation.Routes
import fr.agesfarouches.abysslarp.viewmodels.pages_principales.LoginState
import fr.agesfarouches.abysslarp.viewmodels.pages_principales.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController,
                viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory))
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val state =  viewModel.state

    //check tout le temps les changmement de state
    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            navController.navigate(Routes.HOME) {
                //supprime toutes les pages de l'historique
                popUpTo(Routes.LOGIN_MENU) { inclusive = true }
            }
        }
    }

    //variable preremplie pour aller plus vite dans les tests
    //code à supprimer pour la production
    email = "test@test.com"
    password = "Test1234"

    val fieldShape = RoundedCornerShape(24.dp)
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0xFF6A0DAD),
        unfocusedBorderColor = Color(0xFF6A0DAD),
        focusedLabelColor = Color(0xFF6A0DAD),
        unfocusedLabelColor = Color(0xFF6A0DAD)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "AbyssLarp",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Connexion",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Adresse mail") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            shape = fieldShape,
            colors = fieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            singleLine = true,
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val icon = if (passwordVisible) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }
                val description = if (passwordVisible) "Masquer le mot de passe" else "Afficher le mot de passe"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = description)
                }
            },
            shape = fieldShape,
            colors = fieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (state) {
            is LoginState.Loading -> CircularProgressIndicator()
            is LoginState.Error -> Text(text = state.message, color = Color.Red)
            else -> {}
        }

        Spacer(modifier = Modifier.height(8.dp))

        //le bouton va lancer la fonction login du viewModel
        //qui va changer le state en state.Success
        //qui lancer la page Home avec toute les info charger
        Button(
            onClick = { viewModel.login(email, password) },
            enabled = state !is LoginState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Se connecter")
        }


        Button(
            onClick = {
                // TODO: navigation vers écran mot de passe oublié
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mot de passe oublié")
        }
        Button(
            onClick = {
                // TODO: navigation vers écran mot de passe oublié
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("création d'un compte")
        }
    }
}