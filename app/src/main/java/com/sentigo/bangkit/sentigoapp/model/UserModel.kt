package com.sentigo.bangkit.sentigoapp.model

data class UserModel(
    val id: Int,
    val token: String,
    val isLogin: Boolean,
    val lat: Float? = null,
    val lot: Float? = null
)