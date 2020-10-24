package com.juzonce.customer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.juzonce.customer.R

class ItemPending(val activity: FragmentActivity, val listOfGrids: Int) :
    RecyclerView.Adapter<ItemPending.ViewHolderProducts>() {

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
        return listOfGrids
    }

    override fun onBindViewHolder(holder: ViewHolderProducts, position: Int) {


    }
}