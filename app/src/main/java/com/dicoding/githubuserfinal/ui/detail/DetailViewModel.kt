package com.dicoding.githubuserfinal.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuserfinal.data.UserRepository
import com.dicoding.githubuserfinal.data.local.entity.UserEntity
import kotlinx.coroutines.launch

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isExists = MutableLiveData<UserEntity>()
    val isExists: LiveData<UserEntity> = _isExists

    fun getDetailUser(username: String) = userRepository.getDetailUser(username)

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }

    fun getByUsername(username: String) {
        viewModelScope.launch {
            _isExists.value = userRepository.getByUsername(username)
        }
    }
}