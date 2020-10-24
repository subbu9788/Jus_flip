package com.juzonce.customer.model

import com.google.gson.annotations.SerializedName

data class ModelGetCore(

	@field:SerializedName("details")
	val details: CoreDetails? = null,

	@field:SerializedName("http_status")
	val httpStatus: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class CoreDetails(

	@field:SerializedName("clear_session")
	val clearSession: Int? = null,

	@field:SerializedName("latest_version")
	val latestVersion: Int? = null,

	@field:SerializedName("currency_symbol")
	val currencySymbol: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("force_update")
	val forceUpdate: Int? = null,

	@field:SerializedName("currency_code")
	val currencyCode: String? = null
)
