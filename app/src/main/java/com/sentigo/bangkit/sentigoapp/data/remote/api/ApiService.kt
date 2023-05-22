package com.sentigo.bangkit.sentigoapp.data.remote.api

import com.sentigo.bangkit.sentigoapp.data.remote.response.LoginResponse
import com.sentigo.bangkit.sentigoapp.data.remote.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ) : LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : RegisterResponse
}