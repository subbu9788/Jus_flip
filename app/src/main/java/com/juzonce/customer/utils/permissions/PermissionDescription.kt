package com.juzonce.customer.utils.permissions

import androidx.fragment.app.FragmentActivity
import com.juzonce.customer.R

class PermissionDescription {

    companion object {

        fun permissionForCameraAndStorage(activity: FragmentActivity): java.util.ArrayList<PermissionDialogInfo> {
            val hashMapsModel = java.util.ArrayList<PermissionDialogInfo>()
            hashMapsModel.add(
                PermissionDialogInfo(
                    activity.getString(R.string.permission_camera),
                    activity.getString(R.string.permission_camera_description)
                )
            )
            hashMapsModel.add(
                PermissionDialogInfo(
                    activity.getString(R.string.permission_storage),
                    activity.getString(R.string.permission_storage_description)
                )
            )
            return hashMapsModel
        }


        fun permissionForCameraAndStorageAndLocation(activity: FragmentActivity): java.util.ArrayList<PermissionDialogInfo> {
            val hashMapsModel = java.util.ArrayList<PermissionDialogInfo>()
            hashMapsModel.add(
                PermissionDialogInfo(
                    activity.getString(R.string.permission_camera),
                    activity.getString(R.string.permission_camera_description)
                )
            )
            hashMapsModel.add(
                PermissionDialogInfo(
                    activity.getString(R.string.permission_storage),
                    activity.getString(R.string.permission_storage_description)
                )
            )
            hashMapsModel.add(
                PermissionDialogInfo(
                    activity.getString(R.string.permission_camera),
                    activity.getString(R.string.permission_camera_description)
                )
            )
            return hashMapsModel
        }


    }
}