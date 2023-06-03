package com.sentigo.bangkit.sentigoapp.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[ID_KEY] ?: 0,
                it[TOKEN_KEY] ?:"",
                it[STATE_KEY] ?: false,
                it[LAT_KEY] ?: 0.0,
                it[LON_KEY] ?: 0.0,
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = user.id
            preferences[TOKEN_KEY] = user.token
            preferences[STATE_KEY] = user.isLogin
        }
    }

    suspend fun setLocation(lat: Double, lon: Double) {
        dataStore.edit { preferences ->
            preferences[LAT_KEY] = lat
            preferences[LON_KEY] = lon
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        private val ID_KEY = intPreferencesKey("id")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val LAT_KEY = doublePreferencesKey("lat")
        private val LON_KEY = doublePreferencesKey("lon")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}