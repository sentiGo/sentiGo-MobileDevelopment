package com.sentigo.bangkit.sentigoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class LoginResult(

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("token")
	val token: String
)
