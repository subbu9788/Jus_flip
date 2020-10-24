package com.juzonce.customer.model

import com.google.gson.annotations.SerializedName

data class PojoBrand(

	@field:SerializedName("details")
	val details: List<DetailsItemBrand> = ArrayList(),

	@field:SerializedName("http_status")
	val httpStatus: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DetailsItemBrand(

	@field:SerializedName("brand_name")
	val brandName: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
