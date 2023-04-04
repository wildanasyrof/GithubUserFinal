package com.dicoding.githubuserfinal.ui.detail.fragment

import androidx.lifecycle.ViewModel
import com.dicoding.githubuserfinal.data.UserRepository

class FollowViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFollowers(username: String) = userRepository.getFollowers(username)

    fun getFollowing(username: String) = userRepository.getFollowing(username)

}