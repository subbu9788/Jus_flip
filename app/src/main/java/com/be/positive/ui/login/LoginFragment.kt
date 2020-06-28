package com.be.positive.ui.login

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import okhttp3.ResponseBody
import retrofit2.Response
import android.text.method.PasswordTransformationMethod
import android.view.*
import androidx.appcompat.app.AppCompatActivity


import androidx.navigation.fragment.findNavController

import com.be.positive.utils.Validator.Companion.validateLogin
import com.be.positive.utils.Validator.Companion.validateResetPassword

import com.google.gson.Gson
import com.kirana.merchant.R
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamAPI.Companion.FORGOT_PASSWORD
import com.be.positive.api.ParamAPI.Companion.LOGIN
import com.be.positive.api.ParamAPI.Companion.RESET_PASSWORD
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.ModelSuccess
import com.be.positive.model.login.ModelLoginSuccess
import com.be.positive.ui.splash.SplashFragment
import com.be.positive.utils.MessageUtils
import com.be.positive.utils.SessionManager
import com.be.positive.utils.UiUtils
import com.be.positive.utils.Utils
import kotlinx.android.synthetic.main.dialog_reset_password.*
import kotlinx.android.synthetic.main.dialog_title.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.submit_cancel_horizontal_view.*
import java.lang.Exception


class LoginFragment : Fragment(), ReadWriteAPI, UiUtils {


    var apiReadWrite: ReadWriteAPI? = null
    var sessionMananger: SessionManager? = null
    lateinit var dialog: Dialog
    var token = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as MainActivity).navigationHide()
        (activity as AppCompatActivity).supportActionBar?.show()
        // (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
/*        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiReadWrite = this
        sessionMananger = SessionManager(activity)

        edtEmailID.typeface = MessageUtils.setType(activity, R.font.roboto_slab)
        edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        /*FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAGll", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                token = task.result?.token.toString()

                // Log and toast

                Log.d("TAGll", token)
                Toast.makeText(activity, token, Toast.LENGTH_SHORT).show()
            })
*/

        btnLogin.setOnClickListener {

            val mobileNumber = edtEmailID.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            val map = HashMap<String, String>()
            map[ParamKey.USER_NAME] = mobileNumber
            map[ParamKey.PASSWORD] = password
            map[ParamKey.FIREBASE_TOKEN] = token

            if (validateLogin(this, loginSnackView, map)) {
                APIConnector.callBasic(activity!!, map, apiReadWrite!!, LOGIN)
                /*sessionMananger!!.createLoginSession("Subbu")
                findNavController().navigate(R.id.action_home_after_login)*/
            }

        }
        txtForgotPassword.setOnClickListener {
            try {
                txtForgotPassword.isEnabled = false
                showForgotPassword()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        txtCreateAccount.setOnClickListener {
            /* val navBuilder = NavOptions.Builder()
             val navOptions = navBuilder.setPopUpTo(R.id.nav_slideshow, false).build()
             NavHostFragment.findNavController(ProfileFragment())
                 .navigate(R.id.nav_gallery, null, navOptions)*/

            //findNavController(LoginFragment()).navigate(R.id.action_signInFragment_to_usersFragment)
            findNavController().navigate(R.id.action_register_from_login)
        }

        txtResetPassword.setOnClickListener {
            showResetPassword()
        }
    }

    private fun showForgotPassword() {

        dialog = Dialog(activity!!)
        dialog.setContentView(R.layout.dialog_reset_password)
        dialog.message_title_permission.text = "Forgot Password"
        dialog.iplConfirmPassword.visibility = View.GONE
        dialog.iplNewPassword.visibility = View.GONE
        dialog.edtResetOldPassword.hint = ""
        dialog.edtResetOldPassword.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        dialog.iplOldPassword.hint = "Enter your Email ID"
        dialog.show()
        dialog.setOnDismissListener {
            txtForgotPassword.isEnabled = true
        }
        dialog.btnCancelInHorizontal.setOnClickListener { MessageUtils.dismissDialog(dialog) }
        dialog.btnSubmitInHorizontal.setOnClickListener {
            try {
                val email = dialog.edtResetOldPassword.text.toString()
                if (email.isNotEmpty()) {
                    val isValidEmail = Utils.isValidInputFormat(
                        email, Utils.EMAIL_PATTERN
                    )
                    if (isValidEmail) {
                        val map = HashMap<String, String>()
                        map[ParamKey.USER_NAME] = email
                        APIConnector.callBasic(activity!!, map, this, ParamAPI.FORGOT_PASSWORD)
                    } else {
                        showSnackView("Enter Valid Email ID", dialog.dialogSnackView)
                    }
                } else {
                    showSnackView("Enter Email ID", dialog.dialogSnackView)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


    /**
     * User Will reset password with our Current Password
     */
    private fun showResetPassword() {
        dialog = Dialog(activity!!)
        dialog.setContentView(R.layout.dialog_reset_password)
        dialog.show()
        dialog.btnSubmitInHorizontal.text = "Reset"
        dialog.message_title_permission.text = "Reset Password"

        dialog.btnCancelInHorizontal.setOnClickListener { MessageUtils.dismissDialog(dialog) }
        dialog.btnSubmitInHorizontal.setOnClickListener {

            try {

                val currentPassword = dialog.edtResetOldPassword.text.toString().trim()
                val newPassword = dialog.edtResetNewPassword.text.toString().trim()
                val confirmPassword = dialog.edtResetConfirmPassword.text.toString().trim()
                val map = HashMap<String, String>()
                map[ParamKey.OLD_PASSWORD] = currentPassword
                map[ParamKey.NEW_PASSWORD] = newPassword
                map[ParamKey.CONFIRM_PASSWORD] = confirmPassword
                map[ParamKey.USER_ID] = SessionManager.getObject(activity).id.toString()

                if (validateResetPassword(activity!!, dialog.dialogSnackView, map)) {
                    APIConnector.callBasic(activity!!, map, this, RESET_PASSWORD)
                }


            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }


    }

    override fun showSnackView(message: String, view: View) {
        MessageUtils.dismissSnackBar(SplashFragment.snackbar)
        SplashFragment.snackbar = MessageUtils.showSnackBar(activity!!, view, message)
    }


    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        when (api) {
            LOGIN -> {
                try {
                    val modelSuccess =
                        Gson().fromJson(
                            responseBody.body()?.string(),
                            ModelLoginSuccess::class.java
                        )
                    if (modelSuccess != null) {
                        if (modelSuccess.status!!) {
                            sessionMananger!!.createLoginSession(
                                modelSuccess.data!!.userdetails,
                                modelSuccess.subscription!!
                            )
                            findNavController().navigate(R.id.action_dashboard_after_login)
                        } else {
                            showSnackView(
                                modelSuccess.message.toString(),
                                loginSnackView

                            )
                        }
                    } else {
                        showSnackView(
                            activity!!.getString(R.string.something_went_wrong),
                            loginSnackView
                        )
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            RESET_PASSWORD -> {
                val modelSuccess =
                    Gson().fromJson(responseBody.body()?.string(), ModelSuccess::class.java)
                if (modelSuccess != null) {
                    if (modelSuccess.status!!) {
                        MessageUtils.dismissDialog(dialog)
                        MessageUtils.showToastMessage(activity!!, modelSuccess.message, false)
                    } else {
                        showSnackView(
                            modelSuccess.message.toString(),
                            dialog.dialogSnackView

                        )
                    }
                } else {
                    showSnackView(
                        activity!!.getString(R.string.something_went_wrong),
                        dialog.dialogSnackView
                    )

                }
            }
            FORGOT_PASSWORD -> {
                val modelSuccess =
                    Gson().fromJson(responseBody.body()?.string(), ModelSuccess::class.java)
                if (modelSuccess != null) {
                    if (modelSuccess.status!!) {
                        MessageUtils.dismissDialog(dialog)
                        MessageUtils.showToastMessage(activity!!, modelSuccess.message, false)
                    } else {
                        showSnackView(
                            modelSuccess.message.toString(),
                            dialog.dialogSnackView

                        )
                    }
                } else {
                    showSnackView(
                        activity!!.getString(R.string.something_went_wrong),
                        dialog.dialogSnackView
                    )

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MessageUtils.dismissSnackBar(SplashFragment.snackbar)
    }

    override fun onResponseFailure(message: String, api: String) {

        if (RESET_PASSWORD == api) {
            showSnackView(message, dialog.dialogSnackView)
        } else if (FORGOT_PASSWORD == api) {
            showSnackView(message, dialog.dialogSnackView)
        } else {
            showSnackView(message, loginSnackView)
        }
    }
}