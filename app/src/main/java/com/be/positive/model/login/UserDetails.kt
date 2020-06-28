package com.be.positive.model.login

import com.google.gson.annotations.SerializedName

data class UserDetails(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("enrolment_number")
    val enrolmentNumber: String? = null,

    @field:SerializedName("active")
    val active: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("register_date")
    val registerDate: String? = null,

    @field:SerializedName("advocate_name")
    val advocateName: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("dob")
    val dob: String? = null,

    @field:SerializedName("editcount")
    val editcount: Any? = null,

    @field:SerializedName("user_type_id")
    val userTypeId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: Any? = null
)