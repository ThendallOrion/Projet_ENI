package fr.agesfarouches.abysslarp.viewmodels.pages_principales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.agesfarouches.abysslarp.api.GnListItem
import fr.agesfarouches.abysslarp.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface GnListUiState {
    data object Loading : GnListUiState
    data class Success(val gnList: List<GnListItem>) : GnListUiState
    data class Error(val message: String) : GnListUiState
}

class GnListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<GnListUiState>(GnListUiState.Loading)
    val uiState: StateFlow<GnListUiState> = _uiState

    init {
        fetchGnList()
    }

    fun fetchGnList() {
        viewModelScope.launch {
            _uiState.value = GnListUiState.Loading
            try {
                val response = RetrofitClient.api.getGnList()
                if (response.isSuccessful) {
                    _uiState.value = GnListUiState.Success(response.body().orEmpty())
                } else {
                    _uiState.value = GnListUiState.Error("Erreur serveur : ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = GnListUiState.Error(e.message ?: "Erreur réseau")
            }
        }
    }
}



