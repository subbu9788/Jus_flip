package com.be.positive.adapter.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.be.positive.ui.DynamicFragment
import com.be.positive.ui.productModel.ProductModelFragment.Companion.detailsModels

open class PagerAdapter(val fm: FragmentManager?, val NumOfTabs: Int) :
    FragmentStatePagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return DynamicFragment().newInstance(detailsModels)!!
    }


    override fun getCount(): Int {
        return NumOfTabs
    }
}