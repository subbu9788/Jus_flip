package com.juzonce.customer.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.juzonce.customer.R
import com.juzonce.customer.model.bookingService.DetailsItem
import com.juzonce.customer.utils.MessageUtils
import kotlinx.android.synthetic.main.dialog_internet.*
import kotlinx.android.synthetic.main.dialog_title.*
import kotlinx.android.synthetic.main.item_completed_task.view.*
import kotlinx.android.synthetic.main.submit_cancel_vertical_view.*

class ItemProcessing(
    val activity: FragmentActivity,
    val listOfGrids: List<DetailsItem>,
    val menuName: String
) :
    RecyclerView.Adapter<ItemProcessing.ViewHolderProducts>() {

    class ViewHolderProducts(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtVisitingDate = itemView.txtVisitingDate
        val txtModelName = itemView.txtModelName
        val txtReason = itemView.txtReason
        val txtTechDetails = itemView.txtTechDetails
        val txtOTP = itemView.txtOTP
        val layOfActions = itemView.layOfActions
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
        holder.txtModelName.text = " " + listOfGrids[position].modelName
        holder.txtReason.text = " " + listOfGrids[position].booking_id

        if (listOfGrids[position].technician_name.equals(null) || listOfGrids[position].technician_name.toString()
                .isEmpty()
        ) {
            holder.txtTechDetails.text = " Tech Not Assigned"
            holder.txtTechDetails.setTextColor(activity.resources.getColor(R.color.colorRed))
        } else {
            holder.txtTechDetails.setTextColor(activity.resources.getColor(R.color.colorBlack))
            holder.txtTechDetails.text = " " + listOfGrids[position].technician_name.toString()
        }
        holder.txtVisitingDate.text = " " +
                listOfGrids[position].visitDate + " " + listOfGrids[position].visitTime

        if (menuName == "completed") {
            holder.txtOTP.text = " $ " + listOfGrids[position].totalAmount
            holder.txtOTP.visibility = View.VISIBLE
            holder.txtVisitingDate.visibility = View.GONE
        } else {
            holder.txtVisitingDate.visibility = View.VISIBLE
            if (listOfGrids[position].otp != null) {
                holder.txtOTP.text = " " + listOfGrids[position].otp
                holder.txtOTP.visibility = View.VISIBLE
            } else {
                holder.txtOTP.visibility = View.GONE
            }
        }
        holder.layOfActions.setOnClickListener {
            if (menuName == "inprogress") {
                try {
                    val dialogLogout = Dialog(activity)
                    dialogLogout.show()
                    dialogLogout.setContentView(R.layout.dialog_internet)
                    dialogLogout.message_title_permission.text = "Happy Code"
                    dialogLogout.message.text = listOfGrids[position].happyCode.toString()
                    dialogLogout.btnCancelInVertical.visibility = View.GONE
                    dialogLogout.btnSubmitInVertical.text = "Done"
                    dialogLogout.btnSubmitInVertical.setOnClickListener {
                        MessageUtils.dismissDialog(dialogLogout)

                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }
}