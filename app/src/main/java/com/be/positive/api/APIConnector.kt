package com.be.positive.api

import android.app.Dialog
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.be.positive.receiver.ConnectivityReceiver
import com.be.positive.utils.MessageUtils
import com.be.positive.utils.Utils.Companion.instanceOfRetrofit
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class APIConnector {

    companion object {

        /**
         * Create Custom Method for API Call
         */
        fun callBasic(
            activity: FragmentActivity,
            map: HashMap<String, String>,
            apiReadWrite: ReadWriteAPI,
            api: String
        ) {

            if (ConnectivityReceiver.isConnected()) {
                val dialog = MessageUtils.showDialog(activity)
                try {
                    instanceOfRetrofit().getSomething("$api", map)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Log.e("RESPONSE_FAILURE====>", "" + t.message)
                                MessageUtils.dismissDialog(dialog)
                                apiReadWrite.onResponseFailure(
                                    MessageUtils.getFailureMessage(
                                        t.message
                                    ), api
                                )
                            }

                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                Log.e(
                                    "RESPONSE_SUCCESS=====> ",
                                    "RESPONSE_SUCCESS==>  " + response.code()
                                )
                                MessageUtils.dismissDialog(dialog)
                                try {
                                    if (response.isSuccessful) {
                                        apiReadWrite.onResponseSuccess(response, api)
                                    } else {
                                        apiReadWrite.onResponseFailure(
                                            MessageUtils.getFailureCode(
                                                response.code()
                                            ), api
                                        )
                                    }
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                    apiReadWrite.onResponseFailure(
                                        MessageUtils.getFailureMessage(ex.message),
                                        api
                                    )
                                }
                            }

                        })
                } catch (ex: Exception) {
                    MessageUtils.dismissDialog(dialog)
                    ex.printStackTrace()
                    apiReadWrite.onResponseFailure(MessageUtils.getFailureMessage(ex.message), api)
                }
            } else {
                MessageUtils.showNetworkDialog(activity)
            }
        }


        fun callBasicWithoutDialog(
            activity: FragmentActivity,
            map: HashMap<String, String>,
            apiReadWrite: ReadWriteAPI,
            api: String,
            isShow: Boolean
        ) {
            if (ConnectivityReceiver.isConnected()) {
                var dialog = Dialog(activity)
                if (isShow) {
                    dialog = MessageUtils.showDialog(activity)
                }
                try {
                    instanceOfRetrofit().getSomething(api, map)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Log.e("RESPONSE_FAILURE====>", "" + t.message)
                                if (isShow) {
                                    MessageUtils.dismissDialog(dialog)
                                }
                                apiReadWrite.onResponseFailure(
                                    MessageUtils.getFailureMessage(
                                        t.message
                                    ), api
                                )
                            }

                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                Log.e(
                                    "RESPONSE_SUCCESS=====> ",
                                    "RESPONSE_SUCCESS==>  " + response.code()
                                )
                                if (isShow) {
                                    MessageUtils.dismissDialog(dialog)
                                }
                                try {
                                    if (response.isSuccessful) {
                                        apiReadWrite.onResponseSuccess(response, api)
                                    } else {
                                        apiReadWrite.onResponseFailure(
                                            MessageUtils.getFailureCode(
                                                response.code()
                                            ), api
                                        )
                                    }
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                    apiReadWrite.onResponseFailure(
                                        MessageUtils.getFailureMessage(ex.message),
                                        api
                                    )
                                }
                            }

                        })
                } catch (ex: Exception) {
                    MessageUtils.dismissDialog(dialog)
                    ex.printStackTrace()
                    apiReadWrite.onResponseFailure(MessageUtils.getFailureMessage(ex.message), api)
                }
            } else {
                MessageUtils.showNetworkDialog(activity)
            }
        }

        fun callBasicWithPart(
            activity: FragmentActivity,
            imgMap: java.util.HashMap<String, Any>,
            partMap: ArrayList<MultipartBody.Part>,
            readWriteAPI: ReadWriteAPI,
            api: String
        ) {
            if (ConnectivityReceiver.isConnected()) {
                val dialog = MessageUtils.showDialog(activity)
                try {
                    instanceOfRetrofit().callAPIWithPart(api, imgMap, partMap)
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Log.e("RESPONSE_FAILURE====>", "" + t.message)
                                MessageUtils.dismissDialog(dialog)
                                readWriteAPI.onResponseFailure(
                                    MessageUtils.getFailureMessage(
                                        t.message
                                    ), api
                                )
                            }

                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                Log.e(
                                    "RESPONSE_SUCCESS=====> ",
                                    "RESPONSE_SUCCESS==>  " + response.code()
                                )
                                MessageUtils.dismissDialog(dialog)
                                try {
                                    if (response.isSuccessful) {
                                        readWriteAPI.onResponseSuccess(response, api)
                                    } else {
                                        readWriteAPI.onResponseFailure(
                                            MessageUtils.getFailureCode(
                                                response.code()
                                            ), api
                                        )
                                    }
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                    readWriteAPI.onResponseFailure(
                                        MessageUtils.getFailureMessage(ex.message),
                                        api
                                    )
                                }
                            }

                        })
                } catch (ex: Exception) {
                    MessageUtils.dismissDialog(dialog)
                    ex.printStackTrace()
                    readWriteAPI.onResponseFailure(MessageUtils.getFailureMessage(ex.message), api)
                }
            } else {
                MessageUtils.showNetworkDialog(activity)
            }
        }


        fun callBasicWithMultiPart(
            activity: FragmentActivity,
            imgMap: java.util.HashMap<String, Any>,
            partMap: ArrayList<MultipartBody.Part>,
            partFMBMap: ArrayList<MultipartBody.Part>,
            partLeaseMap: ArrayList<MultipartBody.Part>,
            readWriteAPI: ReadWriteAPI,
            api: String
        ) {
            if (ConnectivityReceiver.isConnected()) {
                val dialog = MessageUtils.showDialog(activity)
                try {
                    instanceOfRetrofit().callAPIWithMultiPart(
                        api,
                        imgMap,
                        partMap,
                        partFMBMap,
                        partLeaseMap
                    )
                        .enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                Log.e("RESPONSE_FAILURE====>", "" + t.message)
                                MessageUtils.dismissDialog(dialog)
                                readWriteAPI.onResponseFailure(
                                    MessageUtils.getFailureMessage(
                                        t.message
                                    ), api
                                )
                            }

                            override fun onResponse(
                                call: Call<ResponseBody>,
                                response: Response<ResponseBody>
                            ) {
                                Log.e(
                                    "RESPONSE_SUCCESS=====> ",
                                    "RESPONSE_SUCCESS==>  " + response.code()
                                )
                                MessageUtils.dismissDialog(dialog)
                                try {
                                    if (response.isSuccessful) {
                                        readWriteAPI.onResponseSuccess(response, api)
                                    } else {
                                        readWriteAPI.onResponseFailure(
                                            MessageUtils.getFailureCode(
                                                response.code()
                                            ), api
                                        )
                                    }
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                    readWriteAPI.onResponseFailure(
                                        MessageUtils.getFailureMessage(ex.message),
                                        api
                                    )
                                }
                            }

                        })
                } catch (ex: Exception) {
                    MessageUtils.dismissDialog(dialog)
                    ex.printStackTrace()
                    readWriteAPI.onResponseFailure(MessageUtils.getFailureMessage(ex.message), api)
                }
            } else {
                MessageUtils.showNetworkDialog(activity)
            }
        }
    }
}