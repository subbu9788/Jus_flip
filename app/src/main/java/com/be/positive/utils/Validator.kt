package com.be.positive.utils

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.be.positive.api.ParamKey
import com.be.positive.ui.login.LoginFragment
import com.be.positive.ui.register.RegisterFragment
import com.be.positive.ui.splash.SplashFragment.Companion.snackbar


/**
 * Validate all in this Single File
 */

class Validator {

    companion object {

        /**
         * Register page Validation
         */
        fun validateRegister(
            activity: RegisterFragment,
            view: View,
            map: java.util.HashMap<String, String>
        ): Boolean {
            val isValid = Utils.isValidInputFormat(
                map[ParamKey.MOBILE_NUMBER].toString(), Utils.FORMAT_MOBILE_NUMBER
            )
            val isValidEmail = Utils.isValidInputFormat(
                map[ParamKey.EMAIL].toString(), Utils.EMAIL_PATTERN
            )
            return when {
                map[ParamKey.NAME].toString().isEmpty() -> {
                    activity.showSnackView("Name can't be empty", view)
                    false
                }
                map[ParamKey.EMAIL].toString().isEmpty() -> {
                    activity.showSnackView("Email can't be empty", view)
                    false
                }
                !isValidEmail -> {
                    activity.showSnackView("Enter valid Email ID", view)
                    false
                }
                map[ParamKey.ADDRESS].toString().isEmpty() -> {
                    activity.showSnackView("Address can't be empty", view)
                    false
                }
                map[ParamKey.MOBILE_NUMBER].toString().isEmpty() -> {
                    activity.showSnackView("Mobile Number can't be empty", view)
                    false
                }
                map[ParamKey.MOBILE_NUMBER].toString().length < 10 -> {
                    activity.showSnackView("Please enter 10 digit Mobile Number", view)
                    false
                }
                !isValid -> {
                    activity.showSnackView("Please enter valid 10 digit Mobile Number", view)
                    false
                }
                map[ParamKey.PASSWORD].toString().isEmpty() -> {
                    activity.showSnackView("Password can't be empty", view)
                    false
                }
                map[ParamKey.CONFIRM_PASSWORD].toString().isEmpty() -> {
                    activity.showSnackView("Confirm Password can't be empty", view)
                    false
                }
                map[ParamKey.CONFIRM_PASSWORD].toString() != map[ParamKey.PASSWORD].toString() -> {
                    activity.showSnackView("Password Not Matched", view)
                    false
                }
                map[ParamKey.PINCODE].toString().isEmpty() -> {
                    activity.showSnackView("Pincode can't be empty", view)
                    false
                }
                map[ParamKey.PINCODE].toString().length != 6 -> {
                    activity.showSnackView("Pincode must be 6 digits", view)
                    false
                }
                else -> true
            }
        }

        /**
         * Reset Password Validation
         */

        fun validateLogin(
            activity: LoginFragment,
            view: View,
            map: java.util.HashMap<String, String>
        ): Boolean {

            val isValid = Utils.isValidInputFormat(
                map[ParamKey.MOBILE_NUMBER].toString(), Utils.EMAIL_PATTERN
            )
            return when {
                map.size == 0 -> {
                    activity.showSnackView("Please Enter all Field", view)
                    false
                }
                map[ParamKey.MOBILE_NUMBER].toString().isEmpty() -> {
                    activity.showSnackView("Mobile Number can't be empty", view)
                    false
                }
                map[ParamKey.MOBILE_NUMBER].toString().length < 10 -> {
                    activity.showSnackView("Please enter 10 digit Mobile Number", view)
                    false
                }
                /*!isValid -> {
                    activity.showSnackView("Please enter valid Email ID", view)
                    false
                }*/
                map[ParamKey.PASSWORD].toString().isEmpty() -> {
                    activity.showSnackView("Password can't be empty", view)
                    false
                }
                map[ParamKey.PASSWORD].toString().length <= 3 -> {
                    activity.showSnackView("Password can't be empty", view)
                    false
                }
                else -> true
            }
        }

        /**
         * Validate for Reset password
         */

        fun validateResetPassword(
            activity: FragmentActivity,
            view: View,
            map: HashMap<String, String>
        ): Boolean {

            return when {
                map.size == 0 -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Please Enter all Field")
                    false
                }
                map[ParamKey.OLD_PASSWORD].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Enter your Current Password")
                    false
                }
                map[ParamKey.NEW_PASSWORD].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Enter your New Password")
                    false
                }
                map[ParamKey.CONFIRM_PASSWORD].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Enter your Confirm Password")
                    false
                }
                map[ParamKey.CONFIRM_PASSWORD].toString() != map[ParamKey.NEW_PASSWORD].toString() -> {
                    snackbar = MessageUtils.showSnackBar(
                        activity,
                        view,
                        "New Password & Confirm Password not Matched"
                    )
                    false
                }
                else -> true
            }
        }

        fun validateBook(
            map: HashMap<String, String>,
            activity: FragmentActivity,
            view: View?
        ): Boolean {
            return when {
                map.size == 0 -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Please Enter all Field")
                    false
                }
                map[ParamKey.DATE].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Select Date")
                    false
                }
                map[ParamKey.TIME].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Select Time")
                    false
                }
                map[ParamKey.REASON].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Enter your reason")
                    false
                }
                else -> true
            }

        }

        fun validateProfile(
            map: java.util.HashMap<String, String>,
            activity: FragmentActivity,
            view: View?
        ): Boolean {
            val isValidEmail = Utils.isValidInputFormat(
                map[ParamKey.EMAIL].toString(), Utils.EMAIL_PATTERN
            )
            return when {
                map.size == 0 -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Please Enter all Field")
                    false
                }
                map[ParamKey.NAME].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Name can't be empty")
                    false
                }
                map[ParamKey.EMAIL].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Email Id can't be empty")
                    false
                }
                !isValidEmail -> {
                    snackbar = MessageUtils.showSnackBar(activity, view, "Enter valid Email ID")
                    false
                }
                map[ParamKey.ADDRESS].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Address can't be empty")
                    false
                }
                map[ParamKey.PINCODE].toString().isEmpty() -> {
                    snackbar =
                        MessageUtils.showSnackBar(activity, view, "Pincode can't be empty")
                    false
                }
                else -> true
            }
        }
    }
}