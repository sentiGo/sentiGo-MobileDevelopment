package com.sentigo.bangkit.sentigoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class FindResponse(

	@field:SerializedName("ListDestinasi")
	val listDestinasi: List<ListDestinasiItem>,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)