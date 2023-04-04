package com.dicoding.githubuserfinal.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuserfinal.data.UserRepository
import com.dicoding.githubuserfinal.data.local.datastore.SettingsPreferences
import com.dicoding.githubuserfinal.di.Injection
import com.dicoding.githubuserfinal.ui.detail.DetailViewModel
import com.dicoding.githubuserfinal.ui.detail.fragment.FollowViewModel
import com.dicoding.githubuserfinal.ui.favorite.FavoriteViewModel
import com.dicoding.githubuserfinal.ui.main.MainViewModel
import com.dicoding.githubuserfinal.ui.settings.SettingsViewModel

class ViewModelFactory private constructor(private val repository: UserRepository, private val pref: SettingsPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository, pref) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context), Injection.provideDataStore(context))
            }.also { instance = it }
    }
}