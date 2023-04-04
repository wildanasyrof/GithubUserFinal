package com.dicoding.githubuserfinal.data.remote.retrofit

import com.dicoding.githubuserfinal.data.remote.response.DetailResponse
import com.dicoding.githubuserfinal.data.remote.response.ItemsUser
import com.dicoding.githubuserfinal.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    suspend fun getUser(@Query("q") query: String): UserResponse

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    suspend fun getDetailUser(@Path("username") username: String): DetailResponse

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    suspend fun getFollowers(@Path("username") username: String): List<ItemsUser>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    suspend fun getFollowing(@Path("username") username: String): List<ItemsUser>
}