package com.be.positive.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.be.positive.model.bookingService.DetailsItem
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.item_completed_task.view.*

class ItemProcessing(val activity: FragmentActivity, val listOfGrids: List<DetailsItem>) :
    RecyclerView.Adapter<ItemProcessing.ViewHolderProducts>() {

    class ViewHolderProducts(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtVisitingDate = itemView.txtVisitingDate
        val txtModelName = itemView.txtModelName
        val txtReason = itemView.txtReason
        val txtTechDetails = itemView.txtTechDetails
    }

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
        holder.txtModelName.text = listOfGrids[position].modelName
        holder.txtReason.text = listOfGrids[position].reason
        if (listOfGrids[position].modelName.toString().isEmpty()) {
            holder.txtTechDetails.text = "Tech Not Assigned"
            holder.txtTechDetails.setTextColor(activity.resources.getColor(R.color.colorRed))
        } else {
            holder.txtTechDetails.setTextColor(activity.resources.getColor(R.color.colorBlack))
            holder.txtTechDetails.text = listOfGrids[position].modelName.toString()
        }
        holder.txtVisitingDate.text =
            listOfGrids[position].visitDate + " " + listOfGrids[position].visitTime
    }
}