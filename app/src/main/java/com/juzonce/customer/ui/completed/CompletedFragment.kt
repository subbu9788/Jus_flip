package com.juzonce.customer.ui.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.adapter.ItemProcessing
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ParamKey
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.bookingService.ModelBookingServices
import com.juzonce.customer.utils.Utils
import kotlinx.android.synthetic.main.fragment_completed.*
import okhttp3.ResponseBody
import retrofit2.Response

class CompletedFragment : BaseFragment(), ReadWriteAPI {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyCompletedView.layoutManager = LinearLayoutManager(activity)

        val map = Utils.getMapDefaultValues(requireActivity())
        map[ParamKey.TRAVEL_STATUS] = "1"
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.COMPLETED_REQUEST)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as MainActivity).disableNavigation(getString(R.string.completed))
        return inflater.inflate(R.layout.fragment_completed, container, false)
    }

    override fun getTitle(): String {
        return getString(R.string.completed)
    }

    override fun getShowHomeToolbar(): Boolean {
        return true
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        val modelSuccess =
            Gson().fromJson(responseBody.body()?.string(), ModelBookingServices::class.java)
        if (modelSuccess != null) {
            if (modelSuccess.status!!) {
                if (modelSuccess.details.size != 0) {
                    txtNoCompleted.visibility = View.GONE
                    rcyCompletedView.visibility = View.VISIBLE
                    rcyCompletedView.adapter =
                        ItemProcessing(requireActivity(), modelSuccess.details, "completed")
                } else {
                    txtNoCompleted.visibility = View.VISIBLE
                    rcyCompletedView.visibility = View.GONE
                    txtNoCompleted.text = "Booking Service Not Found"
                }

            } else {
                txtNoCompleted.visibility = View.VISIBLE
                txtNoCompleted.text = modelSuccess.message.toString()
                rcyCompletedView.visibility = View.GONE

            }
        } else {
            txtNoCompleted.visibility = View.VISIBLE
            txtNoCompleted.text = getString(R.string.something_went_wrong)
            rcyCompletedView.visibility = View.GONE
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        txtNoCompleted.visibility = View.VISIBLE
        txtNoCompleted.text = message.toString()
        rcyCompletedView.visibility = View.GONE
    }
}