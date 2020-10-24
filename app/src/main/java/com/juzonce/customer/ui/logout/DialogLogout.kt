package com.rcd.driver.ui.logout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.juzonce.customer.R
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.ModelSuccess
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.SessionManager
import com.juzonce.customer.utils.Utils
import kotlinx.android.synthetic.main.dialog_logout.*
import okhttp3.ResponseBody
import retrofit2.Response

class DialogLogout : DialogFragment(), ReadWriteAPI {

    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireActivity())
        btnCancelInHorizontal.text = requireActivity().getString(R.string.no)
        btnSubmitInHorizontal.text = requireActivity().getString(R.string.yes)

        btnCancelInHorizontal.setOnClickListener {
            try {
                MessageUtils.dismissDialog(requireDialog())
                findNavController().popBackStack()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        btnSubmitInHorizontal.setOnClickListener {
            //SessionManager.logoutUser(requireActivity())
            val map = Utils.getMapDefaultValues(requireActivity())
            APIConnector.callBasic(requireActivity(), map, this, ParamAPI.LOUGOUT)
        }
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelSuccess::class.java)
            if (modelSuccess.status!!) {
                SessionManager.logoutUser(requireActivity())
            } else {
                MessageUtils.showSnackBar(
                    requireActivity(),
                    layOfDialogSnackView,
                    modelSuccess.message.toString()
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        MessageUtils.showSnackBar(requireActivity(), layOfDialogSnackView, message)
    }
}