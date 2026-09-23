package fr.agesfarouches.abysslarp.viewmodels.nfc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.agesfarouches.abysslarp.api.RetrofitClient
import fr.agesfarouches.abysslarp.api.NfcLookupResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface NfcLookupState {
    data object Idle : NfcLookupState
    data object Loading : NfcLookupState
    data class Found(val result: NfcLookupResponse) : NfcLookupState
    data object Unknown : NfcLookupState
    data class Error(val message: String) : NfcLookupState
}

class NfcViewModel : ViewModel() {

    private val _state = MutableStateFlow<NfcLookupState>(NfcLookupState.Idle)
    val state: StateFlow<NfcLookupState> = _state

    fun lookup(uid: String) {
        viewModelScope.launch {
            _state.value = NfcLookupState.Loading
            try {
                val response = RetrofitClient.api.getNfcInfo(uid)
                when (response.code()) {
                    200 -> response.body()?.let { _state.value = NfcLookupState.Found(it) }
                    404 -> _state.value = NfcLookupState.Unknown
                    else -> _state.value = NfcLookupState.Error("Erreur serveur : ${response.code()}")
                }
            } catch (e: Exception) {
                _state.value = NfcLookupState.Error(e.message ?: "Erreur réseau")
            }
        }
    }

    fun reset() {
        _state.value = NfcLookupState.Idle
    }
}