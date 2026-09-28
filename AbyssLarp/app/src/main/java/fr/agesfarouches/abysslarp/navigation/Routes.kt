package fr.agesfarouches.abysslarp.navigation
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/*navigation 3
NaveKay est l'interphase de navigation

SerializersModule est un module de sérialisation de données
Deux formes possibles :
- data object : écran SANS paramètre (ex : Home, Login)
- data class : écran AVEC paramètres (ex: GnDetailKey(id))
*/

//Pages principales
data object LoginKey : NavKey
data object HomeKey : NavKey
data object ProfilKey : NavKey
@Serializable data object NfcKey : NavKey

@Serializable data object GnListKey : NavKey
@Serializable data class GnDetailKey(val id: Int) : NavKey

//Jeux
@Serializable data object GamePowerKey : NavKey

//Page test
@Serializable data object HomeTestKey : NavKey
@Serializable data object NewPageKey : NavKey
