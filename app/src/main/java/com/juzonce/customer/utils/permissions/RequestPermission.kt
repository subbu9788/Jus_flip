package com.juzonce.customer.utils.permissions

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.juzonce.customer.BaseActivity
import com.juzonce.customer.R


class RequestPermission {

    companion object {

        val PROFILE_IMAGE = 1


        fun checkPermissions(
            requestCode: Int,
            activity: FragmentActivity,
            callbackPermission: CallbackPermission,
            permissionDescription: ArrayList<PermissionDialogInfo>,
            permissions: Array<String>
        ) {
            if (checkPermission(
                    activity,
                    permissions
                )
            ) {
                choosePhotoFromGallery(activity, requestCode)
            } else {
                requestPermission(
                    activity,
                    requestCode,
                    permissions,
                    permissionDescription,
                    callbackPermission
                )
            }
        }


        fun checkPermission(
            activity: FragmentActivity,
            permissions: Array<String>
        ): Boolean {
            var isGrant = false

            for ((index, item) in permissions.withIndex()) {
                val permission = ContextCompat.checkSelfPermission(activity, item)
                isGrant = permission == PackageManager.PERMISSION_GRANTED
            }

            /* val result =
                 ContextCompat.checkSelfPermission(activity, permissions[0])
             val result1 = ContextCompat.checkSelfPermission(activity, permissions[1])
             return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED*/
            return isGrant
        }


        fun requestPermission(
            activity: FragmentActivity,
            requestCode: Int,
            permissions: Array<String>,
            hashMapsModel: ArrayList<PermissionDialogInfo>,
            callbackPermission: CallbackPermission
        ) {
            try {
                val alertLayout = Dialog(activity)
                alertLayout.requestWindowFeature(Window.FEATURE_NO_TITLE)
                alertLayout.setContentView(R.layout.dialog_permission)
                alertLayout.setCancelable(false)
                alertLayout.window!!.setGravity(Gravity.BOTTOM)
                alertLayout.window!!.setBackgroundDrawableResource(R.color.colorDialogTrans)
                //alertLayout.window!!.setWindowAnimations(R.style.upDownDialogAnimation)
                val messageTitlePermission: TextView =
                    alertLayout.findViewById(R.id.message_title_permission)
                val done: TextView = alertLayout.findViewById(R.id.btnSubmitInVertical)
                val cancel: TextView = alertLayout.findViewById(R.id.btnCancelInVertical)
                val mDynamicView: LinearLayout = alertLayout.findViewById(R.id.dynamic)

                messageTitlePermission.text = activity.getString(R.string.permission_required)
                done.text = activity.getString(R.string.grand)
                cancel.text = activity.getString(R.string.dismiss)
                for (i in hashMapsModel.indices) {
                    val vi: LayoutInflater =
                        activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val v: View = vi.inflate(R.layout.dialog_permission_dynamic_item, null)
                    val permissionTitle: TextView = v.findViewById(R.id.message_permission_header)
                    val message: TextView = v.findViewById(R.id.message_permission)
                    permissionTitle.text = hashMapsModel[i].title
                    message.text = hashMapsModel[i].mesaage
                    mDynamicView.addView(
                        v,
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    )
                }

                cancel.setOnClickListener {
                    alertLayout.dismiss()
                    //activity.finish()
                }
                done.setOnClickListener {
                    try {
                        alertLayout.dismiss()

                        BaseActivity.initCallbackPermission(callbackPermission)
                        ActivityCompat.requestPermissions(
                            activity,
                            permissions,
                            requestCode
                        )
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                alertLayout.show()
                //return alertLayout
            } catch (ex: Exception) {
                ex.printStackTrace()
            }


        }


        fun choosePhotoFromGallery(activity: FragmentActivity, requestCode: Int) {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            activity.startActivityForResult(galleryIntent, requestCode)
        }
    }
}