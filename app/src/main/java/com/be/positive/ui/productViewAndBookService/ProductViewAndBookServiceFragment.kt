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
import com.be.positive.BaseFragment
import com.be.positive.utils.Utils
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_product_view_and_book_service.*
import java.util.*


class ProductViewAndBookServiceFragment : BaseFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
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

        edtVisitDate.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog =
                DatePickerDialog(requireActivity(), this, year, month, day)
            datePickerDialog.show()
        }
        edtVisitTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireActivity(),
                this,
                hour,
                minute,
                DateFormat.is24HourFormat(requireActivity())
            )
            timePickerDialog.show()
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
         )*/;
    }
}
