package com.juzonce.customer.utils

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.ui.home.HomeFragment
import kotlinx.android.synthetic.main.item_service.view.*

class DialogService : DialogFragment() {
    lateinit var sessionManager: SessionManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showServiceListDialog()
    }

    private fun showServiceListDialog(/*model: ModelSuccess?*/) {
        Log.d("dialogValid","yes")
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_service_list)
        val rcyServiceList = dialog.findViewById<RecyclerView>(R.id.rcyServiceList)
        val txtTitle = dialog.findViewById<TextView>(R.id.message_title_permission)
        txtTitle.text = "Select Service"
        rcyServiceList.layoutManager = LinearLayoutManager(activity)
        val serviceList = ArrayList<HomeFragment.ServiceModel>()
        var serviceModel = HomeFragment.ServiceModel(R.drawable.logo, "Installation")
        serviceList.add(serviceModel)
        serviceModel = HomeFragment.ServiceModel(R.drawable.logo, "Un Installation")
        serviceList.add(serviceModel)
        serviceModel = HomeFragment.ServiceModel(R.drawable.logo, "Service")
        serviceList.add(serviceModel)
        serviceModel = HomeFragment.ServiceModel(R.drawable.logo, "Repair")
        serviceList.add(serviceModel)
        rcyServiceList.adapter = ItemServiceSelection(requireActivity(), serviceList)
        dialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_service_list, container, false)
    }


    class ItemServiceSelection(
        val activity: FragmentActivity,
        val listOfServices: ArrayList<HomeFragment.ServiceModel>
    ) :
        RecyclerView.Adapter<ServiceHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceHolder {
            return ServiceHolder(
                LayoutInflater.from(activity).inflate(
                    R.layout.item_service,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return listOfServices.size
        }

        override fun onBindViewHolder(holder: ServiceHolder, position: Int) {
            holder.imgServiceLogo.setImageResource(listOfServices[position].img)
            holder.txtServiceName.setText(listOfServices[position].serviceName)
            holder.layOfServiceSelection.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("id", HomeFragment.categoryId)
                MainActivity.navController.navigate(R.id.nav_product_model, bundle)
//                it.findNavController().navigate(R.id.action_check_home_service_list, bundle)
            }
        }
    }

    class ServiceHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        val imgServiceLogo = itemVIew.imgServiceLogo
        val txtServiceName = itemVIew.txtServiceName
        val layOfServiceSelection = itemVIew.layOfServiceSelection
    }
}