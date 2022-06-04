package com.example.githubusersearcher

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        //@Path("id") id: String
        @Query("q") q: String,
        @Query("per_page") per_page: Int
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<FollowerResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponse>>
}