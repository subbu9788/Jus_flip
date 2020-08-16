package com.be.positive.model.bookingService

import com.google.gson.annotations.SerializedName

data class ModelBookingServices(

    @field:SerializedName("details")
    val details: List<DetailsItem> = ArrayList(),

    @field:SerializedName("http_status")
    val httpStatus: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DetailsItem(

    @field:SerializedName("reason")
    val reason: String? = null,

    @field:SerializedName("model_name")
    val modelName: String? = null,

    @field:SerializedName("visit_time")
    val visitTime: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("visit_date")
    val visitDate: String? = null
)
