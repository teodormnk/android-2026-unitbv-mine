package cst.unitbvfmi2026.data
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import cst.unitbvfmi2026.ApplicationController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.authDataStore by preferencesDataStore(name = "auth_prefs") // fisier din memorie, care se va lega la authDataStore (se scriu chestii in el)

object AuthDataStore {
    private val KEY_TOKEN = stringPreferencesKey("auth_token") // ca sa faca mai usor leg cu modul de functionare al DataStore, pt creere de Flow

    suspend fun saveToken(token: String) { // se salveaza token-ul prin apel de instance, care ofera context pt salvare (la baza este clasa context)
        ApplicationController.instance.authDataStore.edit { prefs -> // .edit = pt salvare in prefs, la cheia data, val primita ca param
            prefs[KEY_TOKEN] = token
        }
    }

    suspend fun clearToken() { // acelasi principiu, doar ca nu se salveaza val, ci se sterge
        ApplicationController.instance.authDataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
        }
    }

    val tokenFlow: Flow<String?> = ApplicationController.instance.authDataStore.data.map { prefs -> // acesta ADUCE valoarea, dar o da returnata deja in param
        prefs[KEY_TOKEN]
    }
}