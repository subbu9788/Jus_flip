package com.be.positive.adapter.modelList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kirana.merchant.R
import com.be.positive.model.home.ModelHomeGrid
import kotlinx.android.synthetic.main.item_dynamic.view.*
import java.lang.Exception

class ItemProductModel(val activity: FragmentActivity, val listOfGrids: ArrayList<ModelHomeGrid>) :
    RecyclerView.Adapter<ItemProductModel.ViewHolderProducts>() {

    class ViewHolderProducts(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.imgModelLogo
        val txtModelName = itemView.txtModelName
        val itemProductModel = itemView.itemProductModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProducts {
        return ViewHolderProducts(
            LayoutInflater.from(activity).inflate(
                R.layout.item_dynamic,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfGrids.size
    }

    override fun onBindViewHolder(holder: ViewHolderProducts, position: Int) {
        holder.txtModelName.text = listOfGrids[position].name
        holder.icon.setImageResource(listOfGrids[position].icon)
        holder.itemProductModel.setOnClickListener {
            try {
                // Navigation.createNavigateOnClickListener(R.id.action_dashboard_after_login)
                Log.d("lsdkjfslkdj", "itemProductModel")
                it.findNavController()
                    .navigate(R.id.action_nav_product_model_to_nav_product_view_and_book_service)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}