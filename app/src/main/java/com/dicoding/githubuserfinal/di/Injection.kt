package com.dicoding.githubuserfinal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.githubuserfinal.data.UserRepository
import com.dicoding.githubuserfinal.data.local.datastore.SettingsPreferences
import com.dicoding.githubuserfinal.data.local.room.UserDatabase
import com.dicoding.githubuserfinal.data.remote.retrofit.ApiConfig

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }

    fun provideDataStore(context: Context): SettingsPreferences {
        val dataStore = context.dataStore
        return SettingsPreferences.getInstance(dataStore)
    }
}