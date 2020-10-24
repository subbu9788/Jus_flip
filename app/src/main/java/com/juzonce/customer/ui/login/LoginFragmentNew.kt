package com.juzonce.customer.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ParamKey
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.ModelSuccess
import com.juzonce.customer.model.login.ModelLogin
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.SessionManager
import com.juzonce.customer.utils.Utils
import com.juzonce.customer.utils.Validator
import kotlinx.android.synthetic.main.fragment_login_otp.*
import okhttp3.ResponseBody
import retrofit2.Response

class LoginFragmentNew : BaseFragment(), ReadWriteAPI {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).navigationHide()
        return inflater.inflate(R.layout.fragment_login_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireActivity())
        password_input_layout.visibility = View.GONE
        iplUserName.visibility = View.GONE
        Log.d("firebaseTocke", "" + FirebaseInstanceId.getInstance().token.toString())
        signinBtn.setOnClickListener {
            //findNavController().navigate(R.id.action_dashboard_after_login)
            val map = Utils.getMapDefaultValues(requireActivity())
            map[ParamKey.MOBILE_NUMBER] = userNameField.text.toString()
            map[ParamKey.LOGIN_NAME] = edtUserName.text.toString()

            if (password_input_layout.visibility == View.VISIBLE) {
                map[ParamKey.OTP] = passwordField.text.toString()
                if (Validator.validationLogin(this@LoginFragmentNew, loginSnackView, map)) {
                    if (map[ParamKey.LOGIN_NAME].toString().isNotEmpty()) {
                        if (map[ParamKey.OTP].toString().isNotEmpty()) {
                            APIConnector.callBasic(
                                requireActivity(),
                                map,
                                this,
                                ParamAPI.VERIFY_LOGIN_OTP
                            )
                        } else {
                            showSnackView("Enter OTP", loginSnackView)
                        }
                    } else {
                        showSnackView("Enter Your Name", loginSnackView)
                    }
                }
            } else {
                if (Validator.validationLogin(this, loginSnackView, map)) {
                    APIConnector.callBasic(requireActivity(), map, this, ParamAPI.GENERATE_OTP)
                }
            }
        }
    }

    private fun moveToHomeScreen(modelSuccess: ModelLogin) {
        when (findNavController().currentDestination?.id) {
            R.id.nav_login -> {
                sessionManager.createLoginSession(
                    modelSuccess.userDetails
                )
                findNavController().navigate(R.id.action_dashboard_after_login)
            }
        }
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        if (ParamAPI.GENERATE_OTP == api) {
            val modelSuccess = Gson().fromJson<ModelSuccess>(
                responseBody.body()?.string(),
                ModelSuccess::class.java
            )
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    password_input_layout.visibility = View.VISIBLE
                    showSnackView(modelSuccess.message.toString(), loginSnackView)
                    passwordField.setText(modelSuccess.otp.toString())
                    signinBtn.text = "Verify OTP"
                    iplUserName.visibility = View.VISIBLE
                    edtUserName.setText(modelSuccess.name.toString())

                } else {
                    password_input_layout.visibility = View.GONE
                    showSnackView(modelSuccess.message.toString(), loginSnackView)
                }
            } else {
                password_input_layout.visibility = View.GONE
                showSnackView(getString(R.string.something_went_wrong), loginSnackView)
            }
        } else {
            val modelSuccess = Gson().fromJson<ModelLogin>(
                responseBody.body()?.string(),
                ModelLogin::class.java
            )
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    MessageUtils.showToastMessage(
                        requireActivity(),
                        modelSuccess.message.toString(),
                        false
                    )
                    sessionManager.createLoginSession(modelSuccess.userDetails)
                    moveToHomeScreen(modelSuccess)
                } else {
                    showSnackView(modelSuccess.message.toString(), loginSnackView)
                }
            } else {
                showSnackView(getString(R.string.something_went_wrong), loginSnackView)
            }
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        showSnackView(message, loginSnackView)
    }

    override fun getTitle(): String {
        return getString(R.string.login)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }
}