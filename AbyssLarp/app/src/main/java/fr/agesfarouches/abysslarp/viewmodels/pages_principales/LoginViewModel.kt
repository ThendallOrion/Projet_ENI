package fr.agesfarouches.abysslarp.viewmodels.pages_principales

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import fr.agesfarouches.abysslarp.SessionManager
import fr.agesfarouches.abysslarp.api.LoginRequest
import fr.agesfarouches.abysslarp.api.RetrofitClient
import fr.agesfarouches.abysslarp.utils.JwtUtils
import kotlinx.coroutines.launch

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel(private val sessionManager: SessionManager) : ViewModel() {

    var state by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(email: String, password: String) {
        state = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    val payload = JwtUtils.decode(body.accessToken)

                    if (payload != null) {
                        sessionManager.saveSession(
                            id = payload.id,
                            pseudo = payload.pseudo,
                            email = payload.email,
                            role = payload.role,
                            accessToken = body.accessToken
                        )
                        state = LoginState.Success
                    } else {
                        state = LoginState.Error("Jeton invalide reçu du serveur")
                    }
                } else {
                    state = LoginState.Error("Identifiants incorrects")
                }
            } catch (e: Exception) {
                state = LoginState.Error("Erreur réseau : ${e.message}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                    ?: throw IllegalStateException("Application context non disponible pour LoginViewModel")
                LoginViewModel(SessionManager(application.applicationContext))
            }
        }
    }
}