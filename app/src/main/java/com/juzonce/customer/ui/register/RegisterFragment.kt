package com.juzonce.customer.ui.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ParamKey
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.ModelSuccess
import com.juzonce.customer.ui.splash.SplashFragment.Companion.snackbar
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.SessionManager
import com.juzonce.customer.utils.Utils
import com.juzonce.customer.utils.Validator.Companion.validateRegister
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
        sessionMananger = SessionManager(requireActivity())
        readWriteAPI = this

        btnSubmitInHorizontal.text = getString(R.string.register)
        Utils.checkMobileNumber(edtRegPhoneNumber)
        regPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        regConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        btnSubmitInHorizontal.setOnClickListener {
            val map = HashMap<String, String>()

            map[ParamKey.NAME] = edtRegName.text.toString()
            map[ParamKey.MOBILE_NUMBER] = edtRegPhoneNumber.text.toString()
            map[ParamKey.EMAIL] = edtRegEmailId.text.toString()
            map[ParamKey.ADDRESS] = regEdtAddress.text.toString()
            map[ParamKey.PASSWORD] = regPassword.text.toString()
            map[ParamKey.CONFIRM_PASSWORD] = regConfirmPassword.text.toString()
            map[ParamKey.PINCODE] = regPincide.text.toString()

            if (validateRegister(this, registerSnackView, map)) {

                APIConnector.callBasic(
                    requireActivity(),
                    map,
                    readWriteAPI!!,
                    ParamAPI.REGISTER
                )
            }
        }

        btnCancelInHorizontal.setOnClickListener {
            findNavController().popBackStack()
        }

        /*
        regEdtDOB.setOnClickListener {
            try {
                showDateSelection()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }*/
    }

    private fun showDateSelection() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                /*regEdtDOB.setText(
                    Utils.convertDateFormat(
                        dayOfMonth,
                        monthOfYear,
                        year,
                        DATE_PREFIX
                    )
                )*/
                //regEdtDOB.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
            },
            mYear,
            mMonth,
            mDay
        )


        //datePickerDialog.datePicker.minDate =
        c.add(Calendar.YEAR, -18)
        datePickerDialog.datePicker.maxDate = c.timeInMillis//System.currentTimeMillis()
        datePickerDialog.show()
    }


    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelSuccess::class.java)
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    MessageUtils.showToastMessage(requireActivity(), modelSuccess.message, false)
                    findNavController().popBackStack()
                } else {
                    showSnackView(
                        modelSuccess.message.toString(),
                        registerSnackView

                    )
                }
            } else {
                showSnackView(
                    requireActivity().getString(R.string.something_went_wrong),
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
        snackbar = MessageUtils.showSnackBar(requireActivity(), view, message)
    }
}