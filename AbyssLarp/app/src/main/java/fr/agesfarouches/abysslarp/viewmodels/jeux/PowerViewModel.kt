package fr.agesfarouches.abysslarp.viewmodels.jeux

import androidx.lifecycle.ViewModel
import fr.agesfarouches.abysslarp.screens.jeux.PRODUCTION_GENERATEUR
import fr.agesfarouches.abysslarp.screens.jeux.ZoneVaisseau
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * État immuable de la répartition d'énergie.
 * Toutes les règles métier sont ici ; le ViewModel ne fait que le faire évoluer.
 */
data class EtatEnergie(
    val production: Int = PRODUCTION_GENERATEUR,
    val allocations: Map<ZoneVaisseau, Int> = emptyMap()
) {
    val totalUtilise: Int
        get() = allocations.values.sum()

    val disponible: Int
        get() = production - totalUtilise

    fun allouee(zone: ZoneVaisseau): Int = allocations[zone] ?: 0

    fun peutAjouter(zone: ZoneVaisseau): Boolean =
        allouee(zone) < zone.capaciteMax && disponible > 0

    fun peutRetirer(zone: ZoneVaisseau): Boolean =
        allouee(zone) > 0

    fun ajouter(zone: ZoneVaisseau): EtatEnergie =
        if (peutAjouter(zone)) copy(allocations = allocations + (zone to allouee(zone) + 1)) else this

    fun retirer(zone: ZoneVaisseau): EtatEnergie =
        if (peutRetirer(zone)) copy(allocations = allocations + (zone to allouee(zone) - 1)) else this
}

class PowerViewModel : ViewModel() {

    private val _etat = MutableStateFlow(EtatEnergie())
    val etat: StateFlow<EtatEnergie> = _etat.asStateFlow()

    fun ajouter(zone: ZoneVaisseau) {
        _etat.update { it.ajouter(zone) }
    }

    fun retirer(zone: ZoneVaisseau) {
        _etat.update { it.retirer(zone) }
    }
}