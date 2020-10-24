package com.juzonce.customer.utils


import android.app.ActivityOptions
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.*
import android.text.format.DateFormat
import android.util.Log
import android.util.Pair
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.juzonce.customer.ProfileImageView
import com.juzonce.customer.R
import com.juzonce.customer.api.APIClient
import com.juzonce.customer.api.APIInterface
import com.juzonce.customer.api.ParamKey
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.iid.FirebaseInstanceId
import com.razorpay.Checkout
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.HashMap


class Utils {

    companion object {

        var LAND_COUNT_MAX = 0
        var BANK_COUNT_MAX = 0
        var REGISTERED_LAND_COUNT = 0.0

        val IMAGE_SIZE = 400
        val IMAGE_URL_PATH = "http://13.234.154.50/documents/"
        val IMAGE_URL_PATH_PROFILE = "http://13.234.154.50/farmers/profile_pic/"
        val FORMAT_PAN_CARD = "^[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}\$" //ABCDE1234H
        val FORMAT_IFSC_CODE = "^[A-Za-z]{4}0[A-Z0-9a-z]{6}\$"
        val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val FORMAT_MOBILE_NUMBER = "[6-9]{1}[0-9]{9}"
        val PRIVACY_POLICY = "www.google.com"
        val D_NEW_APPLICATION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

        val D_FORMAL = "dd-MM-yyyy"
        val DATE_PREFIX = "-"


        fun loadPrivacyPolicy(appCompatActivity: AppCompatActivity, url: String) {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(appCompatActivity, R.color.colorPrimary))
            builder.addDefaultShareMenuItem()

            builder.setStartAnimations(
                appCompatActivity,
                R.anim.left_to_right_start,
                R.anim.right_to_left_start
            )
            builder.setExitAnimations(
                appCompatActivity,
                R.anim.right_to_left_end,
                R.anim.left_to_right_end
            )
            builder.setSecondaryToolbarColor(
                ContextCompat.getColor(
                    appCompatActivity,
                    R.color.colorPrimary
                )
            )

            /*
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(appCompatActivity, Uri.parse(url))
            */

            //val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(appCompatActivity, R.color.colorPrimary))
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(appCompatActivity, Uri.parse(url))


        }

        fun checkMobileNumber(phoneNumber: EditText?) {
            try {
                phoneNumber?.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        val input = s.toString()
                        if (input.isNotEmpty() && input.length == 1) {
                            //if (input.length() == 1) {
                            val inputInt = Integer.parseInt(input)
                            if (inputInt >= 6) {

                                //phoneNumber.setText(input)
                            } else {
                                phoneNumber.setText("6")
                                phoneNumber.setSelection(input.length)

                            }
                            //}
                        }
                    }

                    override fun afterTextChanged(s: Editable) {

                    }
                })
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        /**
         * Validate Expected Format Code
         */

        fun isValidInputFormat(input: String, regExp: String): Boolean {
            return input.matches(regExp.toRegex())
        }

        fun showImage(
            appCompatActivity: AppCompatActivity,
            url: String,
            imgProfileImage: View,
            transitionName: String,
            urlFrom: String,
            title: String
        ) {

            val intent = Intent(appCompatActivity, ProfileImageView::class.java)
            intent.putExtra("imageLogo", url)
            intent.putExtra("imageType", urlFrom)
            intent.putExtra("title", title)
            val options: ActivityOptions =
                ActivityOptions.makeSceneTransitionAnimation(
                    appCompatActivity,
                    Pair.create(imgProfileImage, transitionName)
                )
            appCompatActivity.startActivity(intent, options.toBundle())
        }


        fun findAndRemoveDuplicate(bankList: ArrayList<String>): ArrayList<String> {
            val hashSet = HashSet<String>()
            hashSet.addAll(bankList)
            bankList.clear()
            bankList.addAll(hashSet)
            try {
                bankList.sortWith(Comparator { s1, s2 ->
                    s1!!.toLowerCase().compareTo(s2!!.toLowerCase())
                })
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
            return bankList
        }


        fun findDuplicates(values: ArrayList<HashMap<String, Any>>): ArrayList<HashMap<String, Any>> {
            val hashSet = HashSet<HashMap<String, Any>>()
            hashSet.addAll(values)
            values.clear()
            values.addAll(hashSet)
            return values
        }


        fun findDuplicatesFromPart(values: ArrayList<MultipartBody.Part>): ArrayList<MultipartBody.Part> {
            val hashSet = HashSet<MultipartBody.Part>()
            hashSet.addAll(values)
            values.clear()
            values.addAll(hashSet)
            return values
        }

        fun marquee(farmerName: TextView) {
            farmerName.setSingleLine()
            farmerName.ellipsize = TextUtils.TruncateAt.MARQUEE
            farmerName.isSelected = true
            farmerName.marqueeRepeatLimit = -1
        }


        fun convertDateFormat(
            dayOfMonth: Int,
            monthOfYear: Int,
            year: Int,
            formatSymbols: String
        ): String {

            val month = monthOfYear + 1
            var day = "0"
            var monthStr = "0"
            day = if (dayOfMonth < 9) {
                day + "" + dayOfMonth
            } else {
                dayOfMonth.toString()
            }

            monthStr = if (month < 9) {
                monthStr + "" + month
            } else {
                month.toString()
            }
            return "$year$formatSymbols$monthStr$formatSymbols$day"
        }

        fun getAgeFromDOB(dayOfMonth: Int, monthOfYear: Int, year: Int): Int {
            val dateOfYourBirth = GregorianCalendar(year, (monthOfYear + 1), dayOfMonth)
            val today = Calendar.getInstance()
            var yourAge = today.get(Calendar.YEAR) - dateOfYourBirth.get(Calendar.YEAR)
            dateOfYourBirth.add(Calendar.YEAR, yourAge)
            if (today.before(dateOfYourBirth)) {
                yourAge--
            }

            yourAge += 1
            return yourAge
        }


        fun hideSoftKeyboard(view: View, appCompatActivity: AppCompatActivity) {
            val imm =
                appCompatActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showSoftKeyboard(view: View, appCompatActivity: AppCompatActivity) {
            if (view.requestFocus()) {
                val imm =
                    appCompatActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        fun multiPartFromFile(filePath: String, key: String): MultipartBody.Part {
            Log.d("filePathsds", "" + filePath)
            val file = File(filePath)
            val requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), file
            )
            val part = MultipartBody.Part.createFormData(
                key,
                file.name,
                requestBody
            )
            return part
        }

        /**
         * Check File name exists or not
         */
        fun checkFile(fileName: String): Boolean {
            if (fileName.isNotEmpty()) {
                if (File(fileName).exists()) {
                    return true
                }
            }
            return false
        }

        fun stringFromPart(value: String): String {
            return value//RequestBody.create(MediaType.parse("text/plain"), value)
        }

        fun nonEditable(editText: EditText?) {
            editText!!.isFocusableInTouchMode = false
            editText.isClickable = false
            editText.isLongClickable = false
            editText.isClickable = false
            editText.isCursorVisible = false
        }


        fun enableEditable(editText: EditText?) {
            editText!!.isFocusableInTouchMode = true
            editText.isClickable = true
            editText.isLongClickable = true
            editText.isClickable = true
            editText.isCursorVisible = true
        }

        fun replace(str: String): String {
            return str.replace(",", "")
        }

        fun capitalize(capString: String): String {
            val capBuffer = StringBuffer()
            val capMatcher =
                Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString)

            try {

                while (capMatcher.find()) {
                    capMatcher.appendReplacement(
                        capBuffer,
                        capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase()
                    )
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return capMatcher.appendTail(capBuffer).toString()
        }

        fun getFileSize(profilePic: String?): Double {
            val file = File(profilePic)
            // Get length of file in bytes
            val fileSizeInBytes = file.length()
            // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
            val fileSizeInKB = fileSizeInBytes / 1024
            //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            val fileSizeInMB = fileSizeInKB / 1024
            Log.d("fileSizeInKBsd", "" + fileSizeInKB)
            return fileSizeInKB.toDouble()
        }

        /**
         * Convert after dot and create two digits
         */

        fun convertDouble(input: String): Double {
            return BigDecimal(input).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        }


        /**
         * Added Bold with Single TextView
         */
        @RequiresApi(Build.VERSION_CODES.N)
        fun htmlCodeConvert(title: String, input: String): Spanned? {
            if (input.isNotEmpty() && input != null && title.isNotEmpty() && title != null) {
                return Html.fromHtml(
                    "<h4><font color='black'>$title</font></h4> $input",
                    Html.FROM_HTML_MODE_COMPACT
                )
            } else {
                return Html.fromHtml(
                    "", Html.FROM_HTML_MODE_COMPACT
                )
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun htmlCodeConvert(input: String): Spanned? {
            if (input.isNotEmpty() && input != null) {
                return Html.fromHtml(
                    "$input",
                    Html.FROM_HTML_MODE_COMPACT
                )
            }
            return Html.fromHtml(
                "",
                Html.FROM_HTML_MODE_COMPACT
            )
        }


        fun convertDate(input: String, inputFormat: String, outputFormat: String): String {
            var spf = SimpleDateFormat(inputFormat)
            val newDate = spf.parse(input)
            spf = SimpleDateFormat(outputFormat)
            return spf.format(newDate)
        }


        fun instanceOfRetrofit(): APIInterface {
            return APIClient.getClient().create(APIInterface::class.java)
        }

        fun startPayment(activity: FragmentActivity, paymentDetails: HashMap<String, String>) {
            Log.d("paymentDetailss", "" + paymentDetails)
            /*
            *  You need to pass current activity in order to let Razorpay create CheckoutActivity
            * */
            //val activity: Activity = this
            val co = Checkout()

            try {
                val options = JSONObject()
                options.put("name", paymentDetails.get(ParamKey.NAME))
                options.put("description", paymentDetails[ParamKey.DESCRIPTION])
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
                options.put("currency", "INR")
                options.put("amount", paymentDetails.get(ParamKey.AMOUNT))

                val prefill = JSONObject()
                prefill.put("email", paymentDetails[ParamKey.EMAIL])
                prefill.put("contact", paymentDetails[ParamKey.MOBILE_NUMBER])
                options.put("prefill", prefill)
                co.open(activity, options)
            } catch (e: Exception) {
                Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }

        fun getDate(dateFormat: String): CharSequence? {
            val d = Date()
            return DateFormat.format(dateFormat, d.time)

        }


        fun getDateFromDatePicker(activity: FragmentActivity, textView: TextInputEditText) {
            val c = Calendar.getInstance()
            val mYear = c.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                activity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    textView.setText(
                        convertDateFormat(
                            dayOfMonth,
                            monthOfYear,
                            year,
                            DATE_PREFIX
                        )
                    )
                },
                mYear,
                mMonth,
                mDay
            )


            //datePickerDialog.datePicker.minDate =
            // c.add(Calendar.YEAR, -18)
            datePickerDialog.datePicker.minDate = c.timeInMillis//System.currentTimeMillis()
            datePickerDialog.show()
        }

        fun getTimeFromTimePicker(
            fragmentActivity: FragmentActivity?,
            textView: TextInputEditText
        ) {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                fragmentActivity,
                TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
                    var hourOfDay = hourOfDay
                    var AM_PM = " AM"
                    var mm_precede = ""
                    val hourOfDayPlusTwo = hourOfDay + 2
                    //if (hourOfDay >= 9 && 22 > hourOfDay) {
                    if (hourOfDay >= 12) {
                        AM_PM = " PM"
                        if (hourOfDay >= 13 && hourOfDay < 24) {
                            hourOfDay -= 12
                        } else {
                            hourOfDay = 12
                        }
                    } else if (hourOfDay == 0) {
                        hourOfDay = 12
                    }
                    if (minute < 10) {
                        mm_precede = "0"
                    }
                    textView.setText("$hourOfDay:$minute $AM_PM")
                    // textView.setText(selectedHour + ":" + selectedMinute);
                }, hour, minute, false
            ) //Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        fun getMapDefaultValues(requireActivity: FragmentActivity): HashMap<String, String> {
            val token = FirebaseInstanceId.getInstance().token.toString()
            val map = HashMap<String, String>()
            val session = SessionManager(requireActivity)
            if (session.isLoggedIn) {
                map[ParamKey.ID] = SessionManager.getObject(requireActivity).id.toString()
            }
            map[ParamKey.DEVICE_ID] = Build.MANUFACTURER + " " + Build.MODEL
            map[ParamKey.DEVICE_TOKEN] = token
            map[ParamKey.DEVICE_TYPE] = "+91"
            return map
        }
    }

    fun capitalize(capString: String): String {
        val capBuffer = StringBuffer()
        val capMatcher: Matcher =
            Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString)
        while (capMatcher.find()) {
            capMatcher.appendReplacement(
                capBuffer,
                capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase()
            )
        }
        return capMatcher.appendTail(capBuffer).toString()
    }
}