package com.juzonce.customer.ui.splash

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.juzonce.customer.BuildConfig
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.CoreDetails
import com.juzonce.customer.model.ModelGetCore
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.SessionManager
import com.juzonce.customer.utils.Utils
import kotlinx.android.synthetic.main.dialog_fource_update.*
import kotlinx.android.synthetic.main.fragment_splash.*
import okhttp3.ResponseBody
import retrofit2.Response


class SplashFragment : Fragment(), ReadWriteAPI {

    var mHandler = Handler()
    var sessionManager: SessionManager? = null

    companion object {
        //   var modelSuccess: ModelProducts? = null
        const val TAG = "SplashFragment"

        fun newInstanceTest() = SplashFragment()
        var snackbar: Snackbar? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).navigationHide()
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(activity)
        val map = Utils.getMapDefaultValues(requireActivity())
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.GET_CORE)
/*        if (sessionManager!!.isLoggedIn) {
            findNavController().navigate(R.id.action_check_dashboard)
        } else {
            findNavController().navigate(R.id.action_check_login)
        }*/

        /*   txtSplash.setOnClickListener
           {
               //NavHostFragment.findNavController(this).navigate(R.id.nav_login)

               findNavController().navigate(
                   R.id.action_check_login/*, null,
                   NavOptions.Builder()
                       .setPopUpTo(
                           R.id.nav_splash,
                           true
                       ).build()*/
               )
           }*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        MessageUtils.dismissSnackBar(snackbar)
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelGetCore::class.java)
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    if (modelSuccess.details!!.latestVersion!! <= BuildConfig.VERSION_CODE) {
                        if (sessionManager!!.isLoggedIn) {
                            findNavController().navigate(R.id.action_check_dashboard)
                        } else {
                            findNavController().navigate(R.id.action_check_login)
                        }
                    } else {
                        showUpdateDialog(modelSuccess.details)

                    }
                } else {
                    txtSplash.text = modelSuccess.message
                }
            } else {
                txtSplash.text = getString(R.string.something_went_wrong)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun showUpdateDialog(details: CoreDetails) {

        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_fource_update)
        dialog.show()
        dialog.setCancelable(false)
        if (details.forceUpdate == 1) {
            dialog.btnLater.visibility = View.GONE
        } else {
            dialog.btnLater.visibility = View.VISIBLE
            dialog.btnForce.visibility = View.VISIBLE
        }
        dialog.btnForce.setOnClickListener {
            try {
                if (details.clearSession == 1) {
                    SessionManager.isClear(activity)
                }
                val appPackageName: String =
                    requireActivity().getPackageName() // getPackageName() from Context or Activity object

                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$appPackageName")
                        )
                    )
                } catch (anfe: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
        dialog.btnLater.setOnClickListener {
            MessageUtils.dismissDialog(dialog)
            if (sessionManager!!.isLoggedIn) {
                findNavController().navigate(R.id.action_check_dashboard)
            } else {
                findNavController().navigate(R.id.action_check_login)
            }
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        try {
            txtSplash.text = message

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}