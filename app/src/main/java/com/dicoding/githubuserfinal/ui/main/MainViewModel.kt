package com.dicoding.githubuserfinal.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubuserfinal.data.UserRepository
import com.dicoding.githubuserfinal.data.local.datastore.SettingsPreferences

class MainViewModel (private val userRepository: UserRepository, private val pref: SettingsPreferences) : ViewModel() {

    fun getSearchUser(username: String) = userRepository.getSearchUser(username)

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }
}