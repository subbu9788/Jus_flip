package com.juzonce.customer.adapter.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.juzonce.customer.ui.DynamicFragment
import com.juzonce.customer.ui.productModel.ProductModelFragment.Companion.detailsModels

open class PagerAdapter(val fm: FragmentManager?, val NumOfTabs: Int) :
    FragmentStatePagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return DynamicFragment().newInstance(detailsModels)!!
    }


    override fun getCount(): Int {
        return NumOfTabs
    }
}