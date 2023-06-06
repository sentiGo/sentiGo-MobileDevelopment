package com.sentigo.bangkit.sentigoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdatePhotoResponse(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
