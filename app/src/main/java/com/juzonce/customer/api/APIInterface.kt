package com.juzonce.customer.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {


    /**
     * Passed Only Values like username,mobilenumber ,password ad etc
     */

    //@POST("{path}")
    @POST("index/")
    fun getSomething(
        /*@Path(
            "path", encoded = true  ) path: String*/
        @Query("type") type: String, @Body map: HashMap<String, String>
    ): Call<ResponseBody>


    /**
     * Passed String values with Single Array Multipart values like images and Videos and File(Pdf,etc)
     */
    @Multipart
    @POST("{path}")
    fun callAPIWithPart(
        @Path("path") path: String, @PartMap imgMap: HashMap<String, Any>, @Part part: ArrayList<MultipartBody.Part>
    ): Call<ResponseBody>


    /**
     * Passed String values with Multiple Array Multipart values like images and Videos and File(Pdf,etc)
     */

    @Multipart
    @POST("{path}")
    fun callAPIWithMultiPart(
        @Path("path") path: String, @PartMap imgMap: HashMap<String, Any>, @Part part: ArrayList<MultipartBody.Part>, @Part partFMBMap: ArrayList<MultipartBody.Part>,
        @Part partLeaseMap: ArrayList<MultipartBody.Part>
    ): Call<ResponseBody>

    @GET
    @Streaming
    fun downloadImage(@Url fileUrl: String?): Call<ResponseBody>
}