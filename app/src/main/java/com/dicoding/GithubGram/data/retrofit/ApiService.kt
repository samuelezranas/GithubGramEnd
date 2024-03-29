package com.dicoding.GithubGram.data.retrofit

import com.dicoding.GithubGram.data.response.DetailGithubResponse
import com.dicoding.GithubGram.data.response.GithubResponse
import com.dicoding.GithubGram.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getGithub(
        @Query("q") username: String?
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String?
    ): Call<DetailGithubResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String?
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String?
    ): Call<List<ItemsItem>>
}