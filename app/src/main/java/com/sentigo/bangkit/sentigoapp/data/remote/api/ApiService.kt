package com.sentigo.bangkit.sentigoapp.data.remote.api

import com.sentigo.bangkit.sentigoapp.data.remote.response.*
import retrofit2.http.*

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

    @GET("user/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : UserResponse

    @GET("destinasi")
    suspend fun getRatingDestinasi(
        @Header("Authorization") token: String
    ) : RatingDestinasiResponse

    @GET("detail/{id_destinasi}")
    suspend fun getDestinasiDetail(
        @Header("Authorization") token: String,
        @Path("id_destinasi") id: Int
    ) : DestinasiDetailResponse
}