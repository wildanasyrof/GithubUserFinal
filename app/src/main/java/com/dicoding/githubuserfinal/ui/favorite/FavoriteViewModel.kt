package com.dicoding.githubuserfinal.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserfinal.data.UserRepository
import com.dicoding.githubuserfinal.data.local.entity.UserEntity

class FavoriteViewModel(repository: UserRepository) : ViewModel() {

    val listUser: LiveData<List<UserEntity>> = repository.getLocalUser()

}