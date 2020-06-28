package com.be.positive.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.be.positive.BaseFragment
import com.kirana.merchant.R
import com.be.positive.adapter.ItemProductList
import com.be.positive.model.home.ModelHomeGrid
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.show()

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
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

        rcyHomeProductList.adapter = ItemProductList(requireActivity(), listOfGrids)
        rcyHomeProductList.visibility = View.VISIBLE

    }
}
