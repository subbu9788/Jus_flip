package com.be.positive.ui.processing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemPending
import com.be.positive.api.ReadWriteAPI
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_processing.*
import okhttp3.ResponseBody
import retrofit2.Response

class ProcessingFragment : BaseFragment(), ReadWriteAPI {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyProcessingView.layoutManager = LinearLayoutManager(requireActivity())
        rcyProcessingView.adapter = ItemPending(requireActivity(), 10)
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

    }

    override fun onResponseFailure(message: String, api: String) {

    }
}