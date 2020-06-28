package com.be.positive.ui.productViewAndBookService

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.be.positive.BaseFragment
import com.kirana.merchant.R

class ProductViewAndBookServiceFragment : BaseFragment() {

    private lateinit var slideshowViewModel: ProductViewAndBookServiceViewModel
    override fun getTitle(): String {
        return getString(R.string.menu_slideshow)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(ProductViewAndBookServiceViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_product_view_and_book_service, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
