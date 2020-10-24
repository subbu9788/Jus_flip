package com.juzonce.customer.api

import okhttp3.ResponseBody
import retrofit2.Response

interface ReadWriteAPI {

    /**
     * Response Success from Server call this method
     */
    fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String)


    /**
     * Response Failure or any issue from Server call this method
     */
    fun onResponseFailure(message: String, api: String)
}