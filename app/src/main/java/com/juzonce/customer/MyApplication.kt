package com.juzonce.customer


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.juzonce.customer.intefaceUtils.ConnectivityReceiverListener
import com.juzonce.customer.utils.LocaleHelper


class MyApplication : Application() {

    var connectivityReceiverListener: ConnectivityReceiverListener? = null

    @SuppressLint("MissingSuperCall")
    override fun onCreate() {
        mInstance = this
    }

    companion object {

        private var mInstance: MyApplication? = null
        @Synchronized
        fun getInstance(): MyApplication? {
            return mInstance
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
    }

    fun setConnectivityListener(listener: ConnectivityReceiverListener) {
        connectivityReceiverListener = listener


    }
}