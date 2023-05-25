package com.sentigo.bangkit.sentigoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sentigo.bangkit.sentigoapp.data.AppRepository
import com.sentigo.bangkit.sentigoapp.data.remote.api.ApiConfig
import com.sentigo.bangkit.sentigoapp.model.UserPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")
object Injection {
    fun providerRepository(context: Context) : AppRepository {
        val apiService = ApiConfig.getApiService()
        val preferences = UserPreferences.getInstance(context.dataStore)

        return AppRepository.getInstance(apiService, preferences)
    }
}