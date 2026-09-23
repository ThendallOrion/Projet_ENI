package fr.agesfarouches.abysslarp

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session")

class SessionManager (private val context: Context){

    private val PSEUDO_KEY = stringPreferencesKey("pseudo")
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

    val pseudoFlow: Flow<String?> = context.dataStore.data.map { it[PSEUDO_KEY] }
    val accessTokenFlow: Flow<String?> = context.dataStore.data.map { it[ACCESS_TOKEN_KEY] }

    suspend fun saveSession(pseudo: String, accessToken: String) {
        context.dataStore.edit { prefs ->
            prefs[PSEUDO_KEY] = pseudo
            prefs[ACCESS_TOKEN_KEY] = accessToken
        }
    }

}