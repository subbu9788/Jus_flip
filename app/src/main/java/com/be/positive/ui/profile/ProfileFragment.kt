package com.be.positive.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.PojoProfileDetails
import com.be.positive.model.login.ModelLogin
import com.be.positive.utils.MessageUtils
import com.be.positive.utils.SessionManager
import com.be.positive.utils.Utils
import com.be.positive.utils.Validator
import com.google.gson.Gson
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.ResponseBody
import retrofit2.Response

class ProfileFragment : BaseFragment(), ReadWriteAPI {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(
            "passwordsds",
            "" + SessionManager.getObject(activity).mobileNumber + "  " + SessionManager.getObject(
                activity
            ).password
        )
        getProfile()

        btnUpdate.setOnClickListener {
            try {
                val map = Utils.getMapDefaultValues(requireActivity())
                map[ParamKey.NAME] = edtRegName.text.toString()
                map[ParamKey.EMAIL] = edtRegEmailId.text.toString()
                map[ParamKey.ADDRESS] = regEdtAddress.text.toString()
                map[ParamKey.PINCODE] = regPincode.text.toString()
                map[ParamKey.MOBILE_NUMBER] = edtRegPhoneNumber.text.toString()

                if (Validator.validateProfile(map, requireActivity(), snackViewInProfile)) {
                    APIConnector.callBasic(requireActivity(), map, this, ParamAPI.PROFILE_UPDATE)
                }

            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).disableNavigation(getString(R.string.profile))
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun getProfile() {
        val map = Utils.getMapDefaultValues(requireActivity())
        map[ParamKey.USER_ID] = SessionManager.getObject(activity).id.toString()
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.PROFILE_DETAILS)
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            when (api) {
                ParamAPI.PROFILE_DETAILS -> {
                    val modelCaseType = Gson().fromJson<PojoProfileDetails>(
                        responseBody.body()?.string(),
                        PojoProfileDetails::class.java
                    )
                    if (modelCaseType != null) {
                        if (modelCaseType.status!!) {
                            text_profile.visibility = View.GONE
                            laOfProfilesInfo.visibility = View.VISIBLE
                            edtRegName.setText(modelCaseType.details!!.name)
                            edtRegEmailId.setText(modelCaseType.details!!.email)
                            regEdtAddress.setText(modelCaseType.details!!.address)
                            regPincode.setText(modelCaseType.details!!.pincode)
                        } else {
                            text_profile.visibility = View.VISIBLE
                            text_profile.text = modelCaseType.message.toString()
                            laOfProfilesInfo.visibility = View.GONE
                        }
                    } else {
                        text_profile.visibility = View.VISIBLE
                        text_profile.text = getString(R.string.something_went_wrong)
                        laOfProfilesInfo.visibility = View.GONE
                    }
                }
                ParamAPI.PROFILE_UPDATE -> {
                    val modelSuccess =
                        Gson().fromJson(responseBody.body()?.string(), ModelLogin::class.java)
                    if (modelSuccess != null) {
                        if (modelSuccess.status!!) {
                            MessageUtils.showToastMessageLong(
                                requireActivity(),
                                modelSuccess.message.toString()
                            )
                            edtRegName.requestFocus()
                            SessionManager.updateLoginSession(
                                requireActivity(),
                                modelSuccess.userDetails
                            )

                        } else {
                            showSnackView(modelSuccess.message.toString(), snackViewInProfile)
                        }
                    } else {
                        showSnackView(getString(R.string.something_went_wrong), snackViewInProfile)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        try {
            text_profile.visibility = View.VISIBLE
            text_profile.text = message
            laOfProfilesInfo.visibility = View.GONE
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    override fun getTitle(): String {
        return "Profile"
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }
}