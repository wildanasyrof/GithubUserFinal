package com.dicoding.githubuserfinal.di

import android.content.Context
import com.dicoding.githubuserfinal.data.UserRepository
import com.dicoding.githubuserfinal.data.local.room.UserDatabase
import com.dicoding.githubuserfinal.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}