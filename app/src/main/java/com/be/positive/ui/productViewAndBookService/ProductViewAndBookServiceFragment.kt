package com.be.positive.ui.productViewAndBookService

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.navigation.fragment.findNavController
import com.be.positive.BaseFragment
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.DetailsItemModel
import com.be.positive.model.ModelSuccess
import com.be.positive.ui.productModel.ProductModelFragment
import com.be.positive.ui.productModel.ProductModelFragment.Companion.brand_id
import com.be.positive.ui.productModel.ProductModelFragment.Companion.categoryId
import com.be.positive.utils.ImageUtils
import com.be.positive.utils.MessageUtils
import com.be.positive.utils.Utils
import com.be.positive.utils.Validator
import com.google.gson.Gson
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_product_view_and_book_service.*
import okhttp3.ResponseBody
import okhttp3.internal.Util
import retrofit2.Response
import java.util.*


class ProductViewAndBookServiceFragment : BaseFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, ReadWriteAPI {
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myday = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = requireArguments().getSerializable("items") as DetailsItemModel
        txtBrandName.text = ProductModelFragment.brandName
        txtModelName.text = ProductModelFragment.modelName
        ImageUtils.setImageLive(modelImage, items.modelImage.toString(), activity)

        btnRequest.setOnClickListener {
            try {
                val map = Utils.getMapDefaultValues(requireActivity())
                map[ParamKey.MODEL_ID] = items.modelId.toString()
                map[ParamKey.MODEL_NAME] = items.modelName.toString()
                map[ParamKey.BRAND_ID] = brand_id
                map[ParamKey.CATEGORY_ID] = categoryId
                map[ParamKey.DATE] = edtVisitDate.text.toString()
                map[ParamKey.TIME] = edtVisitTime.text.toString()
                map[ParamKey.REASON] = edtReason.text.toString()
                if (Validator.validateBook(map, requireActivity(), detaliedSnackView)) {
                    APIConnector.callBasic(requireActivity(), map, this, ParamAPI.BOOK)
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        edtVisitDate.setOnClickListener {
           Utils.getDateFromDatePicker(requireActivity(),edtVisitDate)
            /* val calendar: Calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this, year, month, day)
            datePickerDialog.show()*/
        }
        edtVisitTime.setOnClickListener {

            Utils.getTimeFromTimePicker(requireActivity(),edtVisitTime)
            /*val timePickerDialog = TimePickerDialog(
                requireActivity(),
                this,
                hour,
                minute,
                false*//*DateFormat.is24HourFormat(requireActivity())*//*
            )
            timePickerDialog.show()*/
        }
    }

    override fun getTitle(): String {
        return "Book"
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_view_and_book_service, container, false)

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myYear = year
        myday = day
        myMonth = month
        val c = Calendar.getInstance()
        hour = c[Calendar.HOUR]
        minute = c[Calendar.MINUTE]
        Log.d(
            "slkdjklsjd",
            "$myday, $myMonth, $myYear afterconvert " + Utils.convertDateFormat(
                myday,
                myMonth,
                myYear,
                "dd-MM-yyyy"
            )
        )
        edtVisitDate.setText("" + Utils.convertDateFormat(myday, myMonth, myYear, "-"))
        val timePickerDialog = TimePickerDialog(
            requireActivity(),
            this,
            hour,
            minute,
            DateFormat.is24HourFormat(requireActivity())
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute

        edtVisitTime.setText("$myHour:$myMinute")
        /* edtVisitTime.setText(
             "Year: " + myYear + "\n" +
                     "Month: " + myMonth + "\n" +
                     "Day: " + myday + "\n" +
                     "Hour: " + myHour + "\n" +
                     "Minute: " + myMinute
         )*/
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelSuccess::class.java)
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    findNavController().popBackStack()
                    MessageUtils.showToastMessageLong(
                        requireActivity(),
                        modelSuccess.message.toString()
                    )
                } else {
                    showSnackView(modelSuccess.message.toString(), detaliedSnackView)
                }
            } else {
                showSnackView(getString(R.string.something_went_wrong), detaliedSnackView)
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        showSnackView(message, detaliedSnackView)
    }
}
