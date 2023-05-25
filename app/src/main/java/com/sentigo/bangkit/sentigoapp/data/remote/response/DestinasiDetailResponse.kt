package com.sentigo.bangkit.sentigoapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DestinasiDetailResponse(

	@field:SerializedName("detailDestinasi")
	val detailDestinasi: DetailDestinasi,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DetailDestinasi(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("lat")
	val lat: Double
)
