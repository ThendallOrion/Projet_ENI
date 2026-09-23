package fr.agesfarouches.abysslarp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session")

class SessionManager(private val context: Context) {

    private val ID_KEY = intPreferencesKey("id")
    private val PSEUDO_KEY = stringPreferencesKey("pseudo")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val ROLE_KEY = stringPreferencesKey("role")
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

    val idFlow: Flow<Int?> = context.dataStore.data.map { it[ID_KEY] }
    val pseudoFlow: Flow<String?> = context.dataStore.data.map { it[PSEUDO_KEY] }
    val roleFlow: Flow<String?> = context.dataStore.data.map { it[ROLE_KEY] }
    val accessTokenFlow: Flow<String?> = context.dataStore.data.map { it[ACCESS_TOKEN_KEY] }

    suspend fun saveSession(id: Int, pseudo: String, email: String, role: String, accessToken: String) {
        context.dataStore.edit { prefs ->
            prefs[ID_KEY] = id
            prefs[PSEUDO_KEY] = pseudo
            prefs[EMAIL_KEY] = email
            prefs[ROLE_KEY] = role
            prefs[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}