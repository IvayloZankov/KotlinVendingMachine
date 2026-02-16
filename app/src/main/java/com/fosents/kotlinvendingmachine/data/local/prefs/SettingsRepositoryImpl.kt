package com.fosents.kotlinvendingmachine.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fosents.kotlinvendingmachine.domain.repository.SettingsRepository
import com.fosents.kotlinvendingmachine.util.Constants.PREFS_KEY_ID
import com.fosents.kotlinvendingmachine.util.Constants.PREFS_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
): SettingsRepository {

    private object PrefsKey {
        val vendingKey = stringPreferencesKey(name = PREFS_KEY_ID)
    }

    private val dataStore = context.dataStore

    override suspend fun generateVendingId() {
        dataStore.edit {
            prefs -> prefs[PrefsKey.vendingKey] = getRandomString()
        }
    }

    override fun readVendingId(): Flow<String> {
        return dataStore.data.catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map {
                prefs -> prefs[PrefsKey.vendingKey] ?: ""
        }
    }
}

private fun getRandomString(): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..32).map { allowedChars.random() }.joinToString("")
}