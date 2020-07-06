package com.be.positive.ui.productModel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.be.positive.BaseFragment
import com.kirana.merchant.R
import com.be.positive.adapter.pagerAdapter.PagerAdapter
import com.be.positive.utils.NonSwipeableViewPager

class ProductModelFragment : BaseFragment() {


    private lateinit var galleryViewModel: ProductModelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(ProductModelViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_product_model, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun getTitle(): String {
        return getString(R.string.app_name)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Category"
        val tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_home)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.app_name)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_home)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.app_name)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.menu_home)))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.app_name)))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val viewPager = view.findViewById(R.id.pager) as NonSwipeableViewPager
        val adapter = PagerAdapter(requireActivity().supportFragmentManager, tabLayout.tabCount)
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
