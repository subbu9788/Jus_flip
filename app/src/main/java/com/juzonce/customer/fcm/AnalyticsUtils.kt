package com.juzonce.customer.fcm

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.juzonce.customer.MainActivity.Companion.firebaseAnalytics


class AnalyticsUtils {

    companion object {

        fun recordScreenView(activity: FragmentActivity, screenName: String) {
            Log.d("screenNamesd", "$screenName activity $activity")
            // This string must be <= 36 characters long in order for setCurrentScreen to succeed.
            //  val screenName = "${getCurrentImageId()}-${getCurrentImageTitle()}"
            if (firebaseAnalytics != null) {
                // [START set_current_screen]
                firebaseAnalytics.setCurrentScreen(activity, screenName, null /* class override */)
                // [END set_current_screen]
            }
        }

        fun setProperty(item: String) {
            if (firebaseAnalytics != null) {
                firebaseAnalytics.setUserProperty("screen_name", item)
            }
        }
    }


}