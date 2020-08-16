package com.be.positive.adapter.modelList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.be.positive.model.DetailsItemModel
import com.be.positive.ui.productModel.ProductModelFragment.Companion.modelName
import com.be.positive.utils.ImageUtils
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.item_dynamic.view.*

class ItemProductModel(
    val activity: FragmentActivity,
    val listOfGrids: ArrayList<DetailsItemModel>
) :
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
        holder.txtModelName.text = listOfGrids[position].modelName
        ImageUtils.setImageLive(holder.icon, listOfGrids[position].modelImage, activity)
        holder.itemProductModel.setOnClickListener {
            try {
                modelName = listOfGrids[position].modelName.toString()
                val bunlde = Bundle()
                bunlde.putSerializable("items", listOfGrids[position])
                it.findNavController()
                    .navigate(
                        R.id.action_nav_product_model_to_nav_product_view_and_book_service,
                        bunlde
                    )
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}