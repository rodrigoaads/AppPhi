package com.rodrigoads.appphi.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "balance_visibility")

class DataStoreService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getDataStoreBalanceVisibility(): Flow<Boolean> {
        val getVisibilityKey = booleanPreferencesKey("visibility_pref")
        return context.dataStore.data.map { visibility ->
            visibility[getVisibilityKey] ?: false
        }
    }

    suspend fun setDataStoreBalanceVisibility(setupVisibility: Boolean) {
        val getVisibilityKey = booleanPreferencesKey("visibility_pref")
        context.dataStore.edit { visibility ->
            visibility[getVisibilityKey] = setupVisibility
        }
    }
}