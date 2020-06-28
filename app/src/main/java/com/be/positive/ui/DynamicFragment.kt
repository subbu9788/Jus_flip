package com.be.positive.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.be.positive.BaseFragment
import com.kirana.merchant.R
import com.be.positive.adapter.modelList.ItemProductModel
import com.be.positive.model.home.ModelHomeGrid
import kotlinx.android.synthetic.main.fragment_dynamic_categroy.*


class DynamicFragment : BaseFragment() {

    override fun getTitle(): String {
        return "Dynamic"
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
        return layoutInflater.inflate(R.layout.fragment_dynamic_categroy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val input = requireArguments().getInt("someInt", 0)
        Log.d("inputsds", "" + input)
        txtNoDynamic.text = "Tab Number $input"
        txtNoDynamic.visibility = View.GONE

        val listOfGrids = ArrayList<ModelHomeGrid>()
        var modelHomeGrid = ModelHomeGrid("Smasung", R.drawable.l_samsung)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Sony", R.drawable.l_sony)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Nokia", R.drawable.l_nokia)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Voltas", R.drawable.l_voltas)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Mi", R.drawable.l_mi)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Moto", R.drawable.l_moto)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Havells", R.drawable.l_havells)
        listOfGrids.add(modelHomeGrid)
        modelHomeGrid = ModelHomeGrid("Lloyd", R.drawable.l_lloyd)
        listOfGrids.add(modelHomeGrid)

        rcyDynamicList.layoutManager = LinearLayoutManager(requireActivity())
        rcyDynamicList.adapter = ItemProductModel(requireActivity(), listOfGrids)

    }
}