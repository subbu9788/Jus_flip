package com.be.positive.ui.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.login.ModelLoginSuccess
import com.be.positive.ui.splash.SplashFragment.Companion.snackbar
import com.be.positive.utils.MessageUtils
import com.be.positive.utils.SessionManager
import com.be.positive.utils.Utils
import com.be.positive.utils.Utils.Companion.DATE_PREFIX
import com.google.gson.Gson
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.submit_cancel_horizontal_view.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*


class RegisterFragment : BaseFragment(), ReadWriteAPI {

    private lateinit var registerViewModel: RegisterViewModel
    var readWriteAPI: ReadWriteAPI? = null
    private lateinit var sessionMananger: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).disableNavigation(getString(R.string.register))
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionMananger = SessionManager(activity!!)
        readWriteAPI = this

        btnSubmitInHorizontal.text = getString(R.string.register)
        Utils.checkMobileNumber(edtRegPhoneNumber)

        regEdtOfficeName.setOnClickListener { regSpOfficeName.performClick() }
        edtDistrict.setOnClickListener { spDistrict.performClick() }

        btnSubmitInHorizontal.setOnClickListener {
            /* val map = HashMap<String, String>()
             map[ParamKey.ADVOCATE_NAME] = edtRegAdvocateName.text.toString()
             map[ParamKey.MOBILE_NUMBER] = edtRegPhoneNumber.text.toString()
             map[ParamKey.EMAIL] = edtRegEmailId.text.toString()
             map[ParamKey.ENTROLMENT_NUMBER] = edtRegEntlNumber.text.toString()
             map[ParamKey.ADDRESS] = regEdtAddress.text.toString()
             map[ParamKey.DATE_OF_BIRTH] = regEdtDOB.text.toString()
             map[ParamKey.AGENT_NUMBER_CODE] = regAgentNumberCode.text.toString()

             if (validateRegister(this, registerSnackView, map)) {
                 if (chAgree.isChecked) {
                     APIConnector.callBasic(activity!!, map, readWriteAPI!!, ParamAPI.REGISTER)
                 } else {
                     showSnackView("Please Confirm Our Terms & Conditions", registerSnackView)
                 }
             }*/
        }

        btnCancelInHorizontal.setOnClickListener {
            findNavController().popBackStack()
        }

        regEdtDOB.setOnClickListener {
            try {
                showDateSelection()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun showDateSelection() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            activity!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                regEdtDOB.setText(
                    Utils.convertDateFormat(
                        dayOfMonth,
                        monthOfYear,
                        year,
                        DATE_PREFIX
                    )
                )
                //regEdtDOB.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            }, mYear, mMonth, mDay
        )


        //datePickerDialog.datePicker.minDate =
        c.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.maxDate = c.timeInMillis//System.currentTimeMillis()
        datePickerDialog.show()
    }


    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelLoginSuccess::class.java)
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    /*MessageUtils.showToastMessage(activity!!, modelSuccess.message, false)
                    findNavController().popBackStack()*/
                    sessionMananger!!.createLoginSession(
                        modelSuccess.data!!.userdetails,
                        modelSuccess.subscription!!
                    )
                    MessageUtils.showToastMessage(activity!!, modelSuccess.message, false)
                    findNavController().popBackStack()
                    //findNavController().navigate(R.id.action_home_after_login)
                } else {
                    showSnackView(
                        modelSuccess.message.toString(),
                        registerSnackView

                    )
                }
            } else {
                showSnackView(
                    activity!!.getString(R.string.something_went_wrong),
                    registerSnackView
                )

            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

    }

    override fun onResponseFailure(message: String, api: String) {
        showSnackView(message, registerSnackView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MessageUtils.dismissSnackBar(snackbar)
    }

    override fun getTitle(): String {

        return getString(R.string.register)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun showSnackView(message: String, view: View) {
        MessageUtils.dismissSnackBar(snackbar)
        snackbar = MessageUtils.showSnackBar(activity!!, view, message)
    }
}