package com.sentigo.bangkit.sentigoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RatingDestinasiResponse(

	@field:SerializedName("ListDestinasi")
	val listDestinasi: List<ListDestinasiItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ListDestinasiItem(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("city")
	val city: String
)
