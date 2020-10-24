package com.juzonce.customer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.adapter.modelList.ItemProductModel
import com.juzonce.customer.model.DetailsItemModel
import com.juzonce.customer.ui.productModel.ProductModelFragment.Companion.detailsModels
import kotlinx.android.synthetic.main.fragment_dynamic_categroy.view.*

class DynamicFragment : BaseFragment() {

    companion object {


        lateinit var txtNo: TextView
        lateinit var rcyList: RecyclerView

        fun refreshView(detailsModel: ArrayList<DetailsItemModel>, activity: FragmentActivity) {
            if (detailsModel.size != 0) {
                txtNo.visibility = View.GONE
                rcyList.layoutManager = LinearLayoutManager(activity)
                rcyList.adapter = ItemProductModel(activity, detailsModel)
            } else {
                rcyList.visibility = View.GONE
                txtNo.visibility = View.VISIBLE
                txtNo.text = "Models Not Found"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcyList = view.rcyDynamicList
        txtNo = view.txtNoDynamic
        val input = requireArguments().getSerializable("someInt") as ArrayList<DetailsItemModel>

        if (detailsModels.size != 0) {
            txtNo.visibility = View.GONE
            rcyList.layoutManager = LinearLayoutManager(requireActivity())
            rcyList.adapter = ItemProductModel(requireActivity(), detailsModels)
        } else {
            rcyList.visibility = View.GONE
            txtNo.visibility = View.VISIBLE
            txtNo.text = "Models Not Found"
        }

    }

    override fun getTitle(): String {
        return getString(R.string.products)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }


    fun newInstance(input: ArrayList<DetailsItemModel>): DynamicFragment? {
        val fragment = DynamicFragment()
        val args = Bundle()
        args.putSerializable("someInt", input)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (activity as MainActivity).disableNavigation(getString(R.string.products))
        return layoutInflater.inflate(R.layout.fragment_dynamic_categroy, container, false)
    }

}