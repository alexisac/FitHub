package com.example.fithub.sessionManager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore("session_prefs")

@Singleton
class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map{ prefs -> prefs[DARK_THEME] ?: true }

    suspend fun saveDarkTheme(isDarkTheme: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME] = isDarkTheme
        }
    }
}