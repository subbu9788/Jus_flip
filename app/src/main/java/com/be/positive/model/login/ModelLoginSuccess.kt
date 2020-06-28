package com.be.positive.model.login

import com.google.gson.annotations.SerializedName

data class ModelLoginSuccess(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("subscription")
    val subscription: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)