package com.juzonce.customer.utils.permissions

import android.content.Intent


interface CallbackPermission {

    /**
     * Goto File Manager and Pick Files after Permission Grand
     */
    fun onActionAfterPermissionGrand(requestCode: Int)

    /**
     *After Image Pick and Load Respected View or Activity
     */
    fun activityResultCallback(requestCode: Int, resultCode: Int, data: Intent?)

    /**
     * Something error while Permission Grand
     */
    fun onError(message: String, api: String)


}