package com.juzonce.customer.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.juzonce.customer.R
import com.juzonce.customer.model.CategoryDetailsItem
import com.juzonce.customer.ui.home.HomeFragment.Companion.categoryName
import com.juzonce.customer.utils.ImageUtils
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
        if (listOfGrids[position].status == "1") {
            holder.itemProductLay.setBackgroundColor(activity.resources.getColor(R.color.colorWhite))
            holder.itemProductName.setTextColor(activity.resources.getColor(R.color.colorBlack))
        } else {
            holder.itemProductName.setTextColor(activity.resources.getColor(R.color.colorBlack))
            holder.itemProductLay.setBackgroundColor(activity.resources.getColor(R.color.colorDisable))
        }
        holder.itemProductName.text = listOfGrids[position].categoryName
        ImageUtils.setImageLive(holder.icon, listOfGrids[position].categoryImage, activity)
        holder.itemProductLay.setOnClickListener {
            try {
                if (listOfGrids[position].status == "1") {
                    categoryName = listOfGrids[position].categoryName.toString()
                    val bundle = Bundle()
                    bundle.putString("id", "" + listOfGrids[position].categoryId)
                    it.findNavController()
                        .navigate(R.id.action_sub_category_from_product_list, bundle)
                } else {
                    Log.d("disableeded", "opop")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}