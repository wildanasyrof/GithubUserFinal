package com.dicoding.githubuserfinal.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.githubuserfinal.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY username ASC")
    fun getUser(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(note: UserEntity)

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getByUsername(username: String): UserEntity
}