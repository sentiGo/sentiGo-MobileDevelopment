package com.sentigo.bangkit.sentigoapp.model

data class UserModel(
    val id: Int,
    val token: String,
    val isLogin: Boolean,
    val lat: Double? = null,
    val lon: Double? = null
)