package com.be.positive.ui.processing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemPending
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.bookingService.ModelBookingServices
import com.be.positive.utils.Utils
import com.google.gson.Gson
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_processing.*
import okhttp3.ResponseBody
import retrofit2.Response

class ProcessingFragment : BaseFragment(), ReadWriteAPI {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyProcessingView.layoutManager = LinearLayoutManager(requireActivity())


        val map = Utils.getMapDefaultValues(requireActivity())
        map[ParamKey.TRAVEL_STATUS] = "2"
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.ASSIGN_REQUEST)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as MainActivity).disableNavigation(getString(R.string.processing_task))
        return inflater.inflate(R.layout.fragment_processing, container, false)
    }

    override fun getTitle(): String {
        return getString(
            R.string.processing_task
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
                    rcyProcessingView.adapter = ItemPending(requireActivity(), 10)
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