package fr.agesfarouches.abysslarp.viewmodels.pages_principales

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.agesfarouches.abysslarp.api.GnDetail
import fr.agesfarouches.abysslarp.api.RetrofitClient
import kotlinx.coroutines.launch

sealed class GnDetailState {
    object Loading : GnDetailState()
    data class Success(val gn: GnDetail) : GnDetailState()
    data class Error(val message: String) : GnDetailState()
}

class GnDetailViewModel : ViewModel() {

    var state by mutableStateOf<GnDetailState>(GnDetailState.Loading)
        private set

    fun loadGnDetail(id: Int) {
        state = GnDetailState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getGnDetail(id)
                if (response.isSuccessful && response.body() != null) {
                    state = GnDetailState.Success(response.body()!!)
                } else {
                    state = GnDetailState.Error("GN introuvable")
                }
            } catch (e: Exception) {
                state = GnDetailState.Error("Erreur réseau : ${e.message}")
            }
        }
    }
}