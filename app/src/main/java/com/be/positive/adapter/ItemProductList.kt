package com.be.positive.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.be.positive.model.CategoryDetailsItem
import com.be.positive.ui.home.HomeFragment.Companion.categoryName
import com.be.positive.utils.ImageUtils
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.item_product.view.*

class ItemProductList(val activity: FragmentActivity, val listOfGrids: List<CategoryDetailsItem>) :
    RecyclerView.Adapter<ItemProductList.ViewHolderProducts>() {

    class ViewHolderProducts(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.imageView2
        val itemProductName = itemView.itemProductName
        val itemProductLay = itemView.itemProductLay
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProducts {
        return ViewHolderProducts(
            LayoutInflater.from(activity).inflate(
                R.layout.item_product,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfGrids.size
    }

    override fun onBindViewHolder(holder: ViewHolderProducts, position: Int) {
        holder.itemProductName.text = listOfGrids[position].categoryName
        ImageUtils.setImageLive(holder.icon, listOfGrids[position].categoryImage, activity)
        holder.itemProductLay.setOnClickListener {
            try {
                categoryName = listOfGrids[position].categoryName.toString()
                val bundle = Bundle()
                bundle.putString("id", "" + listOfGrids[position].categoryId)
                it.findNavController().navigate(R.id.action_sub_category_from_product_list, bundle)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}