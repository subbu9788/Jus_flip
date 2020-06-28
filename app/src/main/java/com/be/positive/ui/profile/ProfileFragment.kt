package com.be.positive.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.be.positive.BaseFragment
import com.kirana.merchant.R
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.ModelSuccess
import com.be.positive.utils.SessionManager
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class ProfileFragment : BaseFragment(), ReadWriteAPI {

    private lateinit var galleryViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //  (activity as MainActivity).enableNavigation(getString(R.string.menu_profile), 0)
        galleryViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        /*galleryViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfile()

    }

    private fun getProfile() {

        val map = HashMap<String, String>()
        map[ParamKey.USER_ID] = SessionManager.getObject(activity).id.toString()
        APIConnector.callBasic(activity!!, map, this, ParamAPI.PROFILE)
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