package com.dicoding.githubuserfinal.ui.main

import androidx.lifecycle.ViewModel
import com.dicoding.githubuserfinal.data.UserRepository

class MainViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun getSearchUser(username: String) = userRepository.getSearchUser(username)
}