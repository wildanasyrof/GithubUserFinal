package com.dicoding.githubuserfinal.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.githubuserfinal.data.local.entity.UserEntity
import com.dicoding.githubuserfinal.data.local.room.UserDao
import com.dicoding.githubuserfinal.data.remote.response.DetailResponse
import com.dicoding.githubuserfinal.data.remote.response.ItemsUser
import com.dicoding.githubuserfinal.data.remote.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
){

    fun getSearchUser(username: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(username)
            val users = response.items
            val listUser = users.map { user ->
                UserEntity(
                    user.login,
                    user.avatarUrl
                )
            }
            emit(Result.Success(listUser))
        } catch (e: Exception) {
            Log.d("UserRepository", "getUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailUser(username: String): LiveData<Result<DetailResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("UserRepository", "getDetailUser: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(username: String): LiveData<Result<List<ItemsUser>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("UserRepository", "getFollowers: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(username: String): LiveData<Result<List<ItemsUser>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowing(username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("UserRepository", "getFollowing: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getLocalUser(): LiveData<List<UserEntity>> = userDao.getUser()

    suspend fun getByUsername(username: String): UserEntity = userDao.getByUsername(username)

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userDao)
            }.also { instance = it }
    }
}