package com.sentigo.bangkit.sentigoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("userData")
	val userData: UserData,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class UserData(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
