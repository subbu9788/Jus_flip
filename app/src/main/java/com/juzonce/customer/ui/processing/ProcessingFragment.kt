package com.juzonce.customer.ui.processing

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
import kotlinx.android.synthetic.main.fragment_processing.*
import okhttp3.ResponseBody
import retrofit2.Response

class ProcessingFragment : BaseFragment(), ReadWriteAPI {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyProcessingView.layoutManager = LinearLayoutManager(requireActivity())


        val map = Utils.getMapDefaultValues(requireActivity())
        map[ParamKey.TRAVEL_STATUS] = "3"
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.ASSIGN_REQUEST)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as MainActivity).disableNavigation(getString(R.string.pending_status))
        return inflater.inflate(R.layout.fragment_processing, container, false)
    }

    override fun getTitle(): String {
        return getString(
            R.string.pending_status
        )
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
                    txtNoProcessing.visibility = View.GONE
                    rcyProcessingView.visibility = View.VISIBLE
                    rcyProcessingView.adapter =
                        ItemProcessing(requireActivity(), modelSuccess.details, "inprogress")
                } else {
                    txtNoProcessing.visibility = View.VISIBLE
                    rcyProcessingView.visibility = View.GONE
                    txtNoProcessing.text = "Booking Service Not Found"
                }
            } else {
                txtNoProcessing.visibility = View.VISIBLE
                txtNoProcessing.text = modelSuccess.message.toString()
                rcyProcessingView.visibility = View.GONE

            }
        } else {
            txtNoProcessing.visibility = View.VISIBLE
            txtNoProcessing.text = getString(R.string.something_went_wrong)
            rcyProcessingView.visibility = View.GONE
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        txtNoProcessing.visibility = View.VISIBLE
        txtNoProcessing.text = message.toString()
        rcyProcessingView.visibility = View.GONE
    }
}