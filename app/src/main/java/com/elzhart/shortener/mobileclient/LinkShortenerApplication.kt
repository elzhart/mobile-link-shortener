package com.elzhart.shortener.mobileclient

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.elzhart.shortener.mobileclient.data.UserPreferencesRepository


private const val TOKEN_PREFERENCE_NAME = "token_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = TOKEN_PREFERENCE_NAME
)
/*
 * Custom app entry point for manual dependency injection
 */
class LinkShortenerApplication: Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}