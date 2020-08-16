package com.be.positive.ui.productModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.pagerAdapter.PagerAdapter
import com.be.positive.utils.NonSwipeableViewPager
import com.google.android.material.tabs.TabLayout
import com.kirana.merchant.R

class ProductModelFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).disableNavigation(getString(R.string.products))
        return inflater.inflate(R.layout.fragment_product_model, container, false)
    }

    override fun getTitle(): String {
        return getString(R.string.products)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Category"
        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("Brand Name 1"))
        tabLayout.addTab(tabLayout.newTab().setText("Brand Name 2"))
        tabLayout.addTab(tabLayout.newTab().setText("Brand Name 3"))
        tabLayout.addTab(tabLayout.newTab().setText("Brand Name 4"))
        tabLayout.addTab(tabLayout.newTab().setText("Brand Name 5"))
        tabLayout.addTab(tabLayout.newTab().setText("Brand Name 6"))
        tabLayout.addTab(tabLayout.newTab().setText("Brand Name 7"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val viewPager = view.findViewById(R.id.pager) as NonSwipeableViewPager
        val adapter = PagerAdapter(childFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.currentItem = 0

        if (tabLayout.tabCount == 2) {
            tabLayout.tabMode = TabLayout.MODE_FIXED
        } else {
            tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.currentItem = p0!!.position
            }
        })

    }
}
