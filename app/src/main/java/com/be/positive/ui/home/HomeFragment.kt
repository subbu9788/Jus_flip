package com.be.positive.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.ItemProductList
import com.be.positive.model.home.ModelHomeGrid
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_home.visibility = View.GONE
        rcyHomeProductList.layoutManager = GridLayoutManager(activity, 3)

        val listOfGrids = ArrayList<ModelHomeGrid>()
        var modelHomeGrid = ModelHomeGrid("A/C", R.drawable.l_samsung)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("WASHING MACHINE", R.drawable.l_sony)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("LAPTOP", R.drawable.l_nokia)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("DESKTOP", R.drawable.l_voltas)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("ELECTONICS", R.drawable.l_mi)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("MOBILE", R.drawable.l_moto)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("FAN", R.drawable.l_havells)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("OTHERS", R.drawable.l_lloyd)
        listOfGrids.add(modelHomeGrid)

        rcyHomeProductList.adapter = ItemProductList(requireActivity(), listOfGrids)
        rcyHomeProductList.visibility = View.VISIBLE

    }
}
