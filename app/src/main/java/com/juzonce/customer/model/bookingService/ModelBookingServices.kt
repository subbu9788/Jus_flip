package com.juzonce.customer.model.bookingService

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
    val reason: String = "",

    @field:SerializedName("model_name")
    val modelName: String = "",

    @field:SerializedName("visit_time")
    val visitTime: String = "",

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("total_amount")
    val totalAmount: String? = null,
    @field:SerializedName("booking_id")
    val booking_id: String? = null,

    @field:SerializedName("otp")
    val otp: String? = null,
    @field:SerializedName("trip_complete_otp")
    val happyCode: String? = null,

    @field:SerializedName("technician_name")
    val technician_name: String? = null,

    @field:SerializedName("visit_date")
    val visitDate: String = ""
)
