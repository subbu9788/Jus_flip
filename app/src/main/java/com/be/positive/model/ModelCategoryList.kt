package com.be.positive.model

import com.google.gson.annotations.SerializedName

data class ModelCategoryList(

    @field:SerializedName("details")
    val details: List<CategoryDetailsItem> = ArrayList(),

    @field:SerializedName("http_status")
    val httpStatus: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class CategoryDetailsItem(

    @field:SerializedName("category_image")
    val categoryImage: String? = null,

    @field:SerializedName("category_name")
    val categoryName: String? = null,

    @field:SerializedName("category_id")
    val categoryId: String? = null
)
