package com.be.positive


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.be.positive.intefaceUtils.ConnectivityReceiverListener
import com.be.positive.utils.LocaleHelper


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