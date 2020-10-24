package com.juzonce.customer.model

import com.google.gson.annotations.SerializedName

data class ModelCategoryList(

    @field:SerializedName("details")
    val details: List<CategoryDetailsItem> = ArrayList(),

    @field:SerializedName("image_array")
    val slider: List<SliderImages> = ArrayList(),

    @field:SerializedName("http_status")
    val httpStatus: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class SliderImages(

    @field:SerializedName("image_id")
    val id: String? = null,

    @field:SerializedName("image_name")
    val image: String? = null
)

data class CategoryDetailsItem(

    @field:SerializedName("category_image")
    val categoryImage: String? = null,

    @field:SerializedName("category_name")
    val categoryName: String? = null,
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("category_id")
    val categoryId: String? = null
)
