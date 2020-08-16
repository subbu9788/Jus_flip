package com.be.positive.ui.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemCompleted
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.bookingService.ModelBookingServices
import com.be.positive.utils.Utils
import com.google.gson.Gson
import com.kirana.merchant.R
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
                    rcyCompletedView.adapter = ItemCompleted(requireActivity(), 10)
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