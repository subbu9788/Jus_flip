package com.be.positive.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemProductList
import com.be.positive.api.APIConnector
import com.be.positive.api.ParamAPI
import com.be.positive.api.ReadWriteAPI
import com.be.positive.model.ModelCategoryList
import com.be.positive.utils.Utils
import com.google.gson.Gson
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.ResponseBody
import retrofit2.Response

class HomeFragment : BaseFragment(), ReadWriteAPI {

    companion object {
        var categoryName = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_home.visibility = View.GONE
        val map = Utils.getMapDefaultValues(requireActivity())
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.CATEGORY_LIST)
        rcyHomeProductList.layoutManager = GridLayoutManager(activity, 2)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).enableNavigation(getString(R.string.home_second), 0)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun getTitle(): String {
        return getString(R.string.menu_home)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        val modelSuccess =
            Gson().fromJson(responseBody.body()?.string(), ModelCategoryList::class.java)
        if (modelSuccess != null) {
            if (modelSuccess.status!!) {
                if (modelSuccess.details.size != 0) {
                    text_home.visibility = View.GONE
                    rcyHomeProductList.visibility = View.VISIBLE
                    //rcyHomeProductList.layoutManager = LinearLayoutManager(requireActivity())
                    rcyHomeProductList.layoutManager = GridLayoutManager(activity, 2)
                    rcyHomeProductList.adapter =
                        ItemProductList(requireActivity(), modelSuccess.details)
                } else {
                    text_home.visibility = View.VISIBLE
                    rcyHomeProductList.visibility = View.GONE
                }
            } else {
                text_home.visibility = View.VISIBLE
                text_home.text = modelSuccess.message.toString()
                rcyHomeProductList.visibility = View.GONE
            }
        } else {
            text_home.visibility = View.VISIBLE
            text_home.text = getString(R.string.something_went_wrong)
            rcyHomeProductList.visibility = View.GONE
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        text_home.visibility = View.VISIBLE
        text_home.text = message
        rcyHomeProductList.visibility = View.GONE
    }
}
