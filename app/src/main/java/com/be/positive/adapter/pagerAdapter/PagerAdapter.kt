package com.be.positive.adapter.pagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.be.positive.ui.DynamicFragment
import com.be.positive.ui.profile.ProfileFragment
import com.be.positive.ui.productViewAndBookService.ProductViewAndBookServiceFragment

open class PagerAdapter(val fm: FragmentManager?, val NumOfTabs: Int) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = ProductViewAndBookServiceFragment()
            }

            1 -> {
                fragment = ProfileFragment()
            }
            2 -> {
                fragment = ProductViewAndBookServiceFragment()
            }
            3 -> {
                fragment = ProfileFragment()
            }
            4 -> {
                fragment = ProductViewAndBookServiceFragment()
            }

            5 -> {
                fragment = ProfileFragment()
            }
        }
        return DynamicFragment().newInstance(position)!!
    }


    override fun getCount(): Int {
        return NumOfTabs
    }
}