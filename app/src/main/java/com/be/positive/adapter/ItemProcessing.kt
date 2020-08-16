package com.be.positive.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.be.positive.model.bookingService.DetailsItem
import com.kirana.merchant.R

class ItemProcessing(val activity: FragmentActivity, val listOfGrids: List<DetailsItem>) :
    RecyclerView.Adapter<ItemProcessing.ViewHolderProducts>() {

    class ViewHolderProducts(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProducts {
        return ViewHolderProducts(
            LayoutInflater.from(activity).inflate(
                R.layout.item_completed_task,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfGrids.size
    }

    override fun onBindViewHolder(holder: ViewHolderProducts, position: Int) {

    }
}