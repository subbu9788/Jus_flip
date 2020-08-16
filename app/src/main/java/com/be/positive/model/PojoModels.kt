package com.be.positive.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PojoModels(

    @field:SerializedName("details")
    val details: ArrayList<DetailsItemModel> = ArrayList(),

    @field:SerializedName("http_status")
    val httpStatus: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DetailsItemModel(

    @field:SerializedName("model_name")
    val modelName: String? = null,

    @field:SerializedName("model_image")
    val modelImage: String? = null,

    @field:SerializedName("model_id")
    val modelId: String? = null

) : Serializable
