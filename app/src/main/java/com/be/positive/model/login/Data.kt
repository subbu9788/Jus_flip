package com.be.positive.model.login

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("userdetails")
    val userdetails: UserDetails? = null
)