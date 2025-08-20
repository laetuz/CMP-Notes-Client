package id.neotica.notes.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import id.neotica.notes.domain.TokenData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

class SessionManager(private val dataStore: DataStore<Preferences>) {

    // Preference keys are defined here for common access.
    private object PreferencesKeys {
        val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val KEY_EXPIRATION_TIME = longPreferencesKey("expiration_time")
    }

    /**
     * Saves the complete user token session data to DataStore.
     */
    suspend fun saveToken(tokenData: TokenData) {
        dataStore.edit { preferences ->
            // Use let for safe handling of nullable values.
            tokenData.token?.let { preferences[PreferencesKeys.KEY_AUTH_TOKEN] = it }
            tokenData.refreshToken?.let { preferences[PreferencesKeys.KEY_REFRESH_TOKEN] = it }
            tokenData.expirationTime?.let { preferences[PreferencesKeys.KEY_EXPIRATION_TIME] = it }
        }
    }

    /**
     * Retrieves the user token session data as a Flow.
     */
    val getToken: Flow<TokenData> = dataStore.data
        .map { preferences ->
            TokenData(
                token = preferences[PreferencesKeys.KEY_AUTH_TOKEN],
                refreshToken = preferences[PreferencesKeys.KEY_REFRESH_TOKEN],
                expirationTime = preferences[PreferencesKeys.KEY_EXPIRATION_TIME]
            )
        }

    /**
     * Clears all saved user session data from DataStore.
     */
    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

//typealias PrefsDataStore = DataStore<Preferences>

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )
internal const val dataStoreFileName = "session.preferences_pb"
