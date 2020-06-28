package com.be.positive

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

import com.be.positive.ui.splash.SplashFragment.Companion.snackbar
import com.be.positive.utils.UiUtils
import com.be.positive.utils.permissions.CallbackPermission
import com.be.positive.utils.LocaleHelper
import com.be.positive.utils.MessageUtils
import com.kirana.merchant.R


open class BaseActivity : AppCompatActivity(), UiUtils {

    var doubleBackToExitPressedOnce = false

    companion object {

        var callbackPermission: CallbackPermission? = null
        fun initCallbackPermission(permission: CallbackPermission) {
            callbackPermission = permission
        }

        var className = ""
        fun BaseActivity(clasname: String) {
            className = clasname
        }

        fun getCurrentActivityName(): String {
            return className
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        try {
            val view = currentFocus
            if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                    "android.webkit."
                )
            ) {
                val scrcoords = IntArray(2)
                view.getLocationOnScreen(scrcoords)
                val x = ev.rawX + view.left - scrcoords[0]
                val y = ev.rawY + view.top - scrcoords[1]
                if (x < view.left || x > view.right || y < view.top || y > view.bottom)
                    (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        this.window.decorView.applicationWindowToken,
                        0
                    )
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }

        return super.dispatchTouchEvent(ev)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    if (callbackPermission != null) {
                        callbackPermission!!.activityResultCallback(
                            requestCode,
                            resultCode,
                            data
                        )
                    }
                } else {
                    if (callbackPermission != null) {
                        callbackPermission!!.onError(getString(
                            R.string.something_went_wrong
                        ), "")
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            callbackPermission!!.onError(MessageUtils.getFailureMessage(ex.message), "")
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        //callbackPermission?.permissionCallback(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty()) {
            var isGrand = false
            for ((index, items) in grantResults.withIndex()) {
                isGrand = items == PackageManager.PERMISSION_GRANTED
            }
            if (callbackPermission != null) {
                if (isGrand) {
                    callbackPermission!!.onActionAfterPermissionGrand(requestCode)
                } else {
                    callbackPermission!!.onError(
                        "Permission Denied, You cannot access location data and camera.",
                        ""
                    )
                    //showAdditionalDialog()
                }
            }
        }
    }

    private fun showAdditionalDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageOKCancel("You need to allow access to both the permissions",
                    DialogInterface.OnClickListener { dialog, which ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA
                                ),
                                1
                            )
                        }
                    })
                return
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }


    override fun onBackPressed() {
        /* if (getCurrentActivityName().isNotEmpty()) {
             if (getCurrentActivityName() == "SplashFragment" || getCurrentActivityName() == "MainActivity") {
                 if (doubleBackToExitPressedOnce) {
                     super.onBackPressed()
                     return
                 }

                 this.doubleBackToExitPressedOnce = true
                 Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show()
                 Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
             } else {
                 super.onBackPressed()
             }
         } else {
             super.onBackPressed()
         }*/

        super.onBackPressed()

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        MessageUtils.dismissSnackBar(snackbar)
    }


    override fun showSnackView(message: String, view: View) {
        try {
            MessageUtils.dismissSnackBar(snackbar)
            snackbar = MessageUtils.showSnackBar(this, view, message)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

}