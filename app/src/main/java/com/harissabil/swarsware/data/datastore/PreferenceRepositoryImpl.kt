package com.harissabil.swarsware.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.harissabil.swarsware.common.constant.Constant
import com.harissabil.swarsware.common.constant.Constant.USER_SETTINGS
import com.harissabil.swarsware.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceRepositoryImpl(
    private val context: Context,
) : PreferenceRepository {
    private val readOnlyProperty = preferencesDataStore(name = USER_SETTINGS)

    val Context.dataStore: DataStore<Preferences> by readOnlyProperty

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] == true
        }
    }

    override suspend fun deleteAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = false
        }
    }

    private object PreferenceKeys {
        val APP_ENTRY = booleanPreferencesKey(Constant.APP_ENTRY)
    }
}