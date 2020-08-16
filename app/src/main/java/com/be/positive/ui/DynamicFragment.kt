package com.be.positive.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.be.positive.MainActivity
import com.be.positive.adapter.modelList.ItemProductModel
import com.be.positive.model.home.ModelHomeGrid
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.fragment_dynamic_categroy.*

class DynamicFragment : BaseFragment() {

    override fun getTitle(): String {
        return getString(R.string.products)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    fun newInstance(input: Int): DynamicFragment? {
        val fragment = DynamicFragment()
        val args = Bundle()
        args.putInt("someInt", input)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val input = requireArguments().getInt("someInt", 0)
        Log.d("inputsds", "" + input)
        txtNoDynamic.text = "Tab Number $input"
        txtNoDynamic.visibility = View.GONE

        val listOfGrids = ArrayList<ModelHomeGrid>()
        var modelHomeGrid = ModelHomeGrid("Model Name 1", R.drawable.l_samsung)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Model Name 2", R.drawable.l_sony)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Model Name 3", R.drawable.l_nokia)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Model Name 4", R.drawable.l_voltas)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Model Name 5", R.drawable.l_mi)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Model Name 6", R.drawable.l_moto)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Model Name 7", R.drawable.l_havells)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Model Name 8", R.drawable.l_lloyd)
        listOfGrids.add(modelHomeGrid)

        rcyDynamicList.layoutManager = LinearLayoutManager(requireActivity())
        rcyDynamicList.adapter = ItemProductModel(requireActivity(), listOfGrids)

    }
}