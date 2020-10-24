package com.juzonce.customer.model

import com.google.gson.annotations.SerializedName

data class PojoProfileDetails(

	@field:SerializedName("details")
	val details: Details? = null,

	@field:SerializedName("http_status")
	val httpStatus: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Details(

	@field:SerializedName("pincode")
	val pincode: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null,

	@field:SerializedName("auth_token")
	val authToken: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
