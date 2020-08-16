package com.be.positive.model.login

import com.google.gson.annotations.SerializedName

data class ModelLogin(

	@field:SerializedName("user_details")
	val userDetails: UserDetails? = null,

	@field:SerializedName("http_status")
	val httpStatus: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class UserDetails(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("device_type")
	val deviceType: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null,

	@field:SerializedName("auth_token")
	val authToken: String? = null,

	@field:SerializedName("logged_in_time")
	val loggedInTime: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
