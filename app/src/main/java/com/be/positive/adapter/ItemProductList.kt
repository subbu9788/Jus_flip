package com.be.positive.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kirana.merchant.R
import com.be.positive.model.home.ModelHomeGrid
import kotlinx.android.synthetic.main.item_product.view.*
import java.lang.Exception

class ItemProductList(val activity: FragmentActivity, val listOfGrids: ArrayList<ModelHomeGrid>) :
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
        holder.itemProductName.text = listOfGrids[position].name
        holder.icon.setImageResource(listOfGrids[position].icon)
        holder.itemProductLay.setOnClickListener {
            try {
                // Navigation.createNavigateOnClickListener(R.id.action_dashboard_after_login)
                Log.d("lsdkjfslkdj", "clicked")
                it.findNavController().navigate(R.id.action_sub_category_from_product_list)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}