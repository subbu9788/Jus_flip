package com.be.positive.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.ModelSuccess
import com.be.positive.utils.SessionManager
import com.google.gson.Gson
import com.kirana.merchant.R
import okhttp3.ResponseBody
import retrofit2.Response

class ProfileFragment : BaseFragment(), ReadWriteAPI {

    private lateinit var galleryViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        (activity as MainActivity).disableNavigation(getString(R.string.profile))
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    private fun getProfile() {

        val map = HashMap<String, String>()
        map[ParamKey.USER_ID] = SessionManager.getObject(activity).id.toString()
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.PROFILE)
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            when (api) {
                ParamAPI.PROFILE -> {
                    val modelCaseType = Gson().fromJson<ModelSuccess>(
                        responseBody.body()?.string(),
                        ModelSuccess::class.java
                    )

                    if (modelCaseType != null) {
                        if (modelCaseType.status!!) {

                        } else {

                        }
                    } else {

                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        try {

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