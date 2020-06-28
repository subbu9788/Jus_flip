package com.be.positive.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.kirana.merchant.R
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.ModelSuccess
import com.be.positive.utils.MessageUtils
import com.be.positive.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_splash.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception


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
        //(activity as MainActivity).navigationHide()
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(activity)

        findNavController().navigate(R.id.action_check_dashboard)
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

    override fun onResume() {
        super.onResume()

        /* if (sessionManager!!.isLoggedIn) {
             val map = HashMap<String, String>()
             map[ParamKey.USER_ID] = SessionManager.getObject(activity).id.toString()
             APIConnector.callBasic(activity!!, map, this, ParamAPI.CHECK_SUBSCRIBE)
         } else {
             Thread(Runnable {
                 Thread.sleep(1000)
                 mHandler.post {
                     if (sessionManager!!.isLoggedIn) {
                         findNavController().navigate(R.id.action_check_dashboard)
                     } else {
                         findNavController().navigate(R.id.action_check_login)
                         //findNavController().navigate(R.id.action_check_home)
                     }
                 }
             }).start()
         }*/
        /*val map = HashMap<String, String>()
        APIConnector.callBasic(activity!!, map, this, ParamAPI.CHECK_SUBSCRIBE)*/


    }


    override fun onDestroyView() {
        super.onDestroyView()
        MessageUtils.dismissSnackBar(snackbar)
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {

            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelSuccess::class.java)
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    // if (sessionManager!!.isLoggedIn) {
                    // if (SessionManager.isSubscribe(activity)) {
                    // SessionManager.updateSubscriptionDetails(activity, modelSuccess.subscription!!)
                    findNavController().navigate(R.id.action_check_dashboard)
                    /* } else {
                         findNavController().navigate(R.id.action_check_login)
                         //findNavController().navigate(R.id.action_check_home)
                     }*/
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

    override fun onResponseFailure(message: String, api: String) {
        try {
            txtSplash.text = message

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}