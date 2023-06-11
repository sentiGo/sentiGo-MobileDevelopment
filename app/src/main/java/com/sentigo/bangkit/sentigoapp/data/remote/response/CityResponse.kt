package com.sentigo.bangkit.sentigoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class CityResponse(

	@field:SerializedName("ListDestinasi")
	val listDestinasi: List<ListDestinasiItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
