package com.be.positive.ui.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemCompleted
import com.be.positive.api.ReadWriteAPI
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_completed.*
import okhttp3.ResponseBody
import retrofit2.Response

class CompletedFragment : BaseFragment(), ReadWriteAPI {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyCompletdView.layoutManager = LinearLayoutManager(activity)
        rcyCompletdView.adapter = ItemCompleted(requireActivity(), 10)
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

    }

    override fun onResponseFailure(message: String, api: String) {

    }
}