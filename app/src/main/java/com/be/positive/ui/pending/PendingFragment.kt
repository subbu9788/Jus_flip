package com.be.positive.ui.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemProcessing
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ParamKey
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.bookingService.ModelBookingServices
import com.be.positive.utils.Utils
import com.google.gson.Gson
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_pending.*
import okhttp3.ResponseBody
import retrofit2.Response

class PendingFragment : BaseFragment(), ReadWriteAPI {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyPendingView.layoutManager = LinearLayoutManager(requireActivity())

        val map = Utils.getMapDefaultValues(requireActivity())
        map[ParamKey.TRAVEL_STATUS] = "9"
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.BOOKING_REQUEST)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as MainActivity).disableNavigation(getString(R.string.pending_status))
        return inflater.inflate(R.layout.fragment_pending, container, false)
    }

    override fun getTitle(): String {
        return getString(R.string.pending_status)
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
                    txtNoPending.visibility = View.GONE
                    rcyPendingView.visibility = View.VISIBLE
                    rcyPendingView.adapter = ItemProcessing(requireActivity(), modelSuccess.details)
                } else {
                    txtNoPending.visibility = View.VISIBLE
                    rcyPendingView.visibility = View.GONE
                    txtNoPending.text = "Booking Service Not Found"
                }
            } else {
                txtNoPending.visibility = View.VISIBLE
                txtNoPending.text = modelSuccess.message.toString()
                rcyPendingView.visibility = View.GONE

            }
        } else {
            txtNoPending.visibility = View.VISIBLE
            txtNoPending.text = getString(R.string.something_went_wrong)
            rcyPendingView.visibility = View.GONE
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        txtNoPending.visibility = View.VISIBLE
        txtNoPending.text = message.toString()
        rcyPendingView.visibility = View.GONE
    }
}