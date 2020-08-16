package com.be.positive.ui.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemProcessing
import com.be.positive.api.ReadWriteAPI
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_pending.*
import okhttp3.ResponseBody
import retrofit2.Response

class PendingFragment : BaseFragment(), ReadWriteAPI {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyPendingView.layoutManager = LinearLayoutManager(requireActivity())
        rcyPendingView.adapter=ItemProcessing(requireActivity(),10)
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

    }

    override fun onResponseFailure(message: String, api: String) {

    }
}