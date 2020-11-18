package com.juzonce.customer.ui.home

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.adapter.ItemProductList
import com.juzonce.customer.adapter.slider.SliderAdapterExample
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ParamAPI.Companion.SERVICE_TYPE_LIST
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.intefaceUtils.ProductItemClick
import com.juzonce.customer.model.ModelCategoryList
import com.juzonce.customer.model.sliderMenu.SliderItem
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.Utils
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_service.view.*
import okhttp3.ResponseBody
import retrofit2.Response

class HomeFragment : BaseFragment(), ReadWriteAPI, ProductItemClick {

    companion object {
        var categoryName = ""
        var categoryId = ""
    }

    lateinit var productItemClick: ProductItemClick
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_home.visibility = View.GONE
        val map = Utils.getMapDefaultValues(requireActivity())
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.CATEGORY_LIST)
        rcyHomeProductList.layoutManager = GridLayoutManager(activity, 2)
        // showSliderAndListView()
        productItemClick = this
        txtPending.setOnClickListener {
            try {
                it.findNavController().navigate(R.id.action_pending_from_product_list)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        txtProcessing.setOnClickListener {
            try {
                it.findNavController().navigate(R.id.action_inprogress_from_product_list)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        txtCompleted.setOnClickListener {
            try {
                it.findNavController().navigate(R.id.action_completed_from_product_list)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).enableNavigation(getString(R.string.home_second), 0)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    private fun showSliderAndListView() {

        // val sliderView: SliderView = requireView().imageSlider
        val adapter = SliderAdapterExample(requireActivity())
        imageSlider.setSliderAdapter(adapter)

        var sliderItem =
            SliderItem("https://www.orissahighcourt.nic.in/images/new-slider/Banner-updated-14.jpg")
        adapter.addItem(sliderItem)
        sliderItem =
            SliderItem("https://keys.co.nz/wp-content/uploads/2013/11/Photo-3.5-High-Court-Slider-Choice-2.jpg")
        adapter.addItem(sliderItem)
        sliderItem =
            SliderItem("https://www.orissahighcourt.nic.in/images/new-slider/Banner-updated-2.jpg")
        adapter.addItem(sliderItem)
        // sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        imageSlider.indicatorSelectedColor = Color.WHITE
        imageSlider.indicatorUnselectedColor = Color.TRANSPARENT
        imageSlider.scrollTimeInSec = 2; //set scroll delay in seconds :
        imageSlider.startAutoCycle()
    }

    override fun getTitle(): String {
        return getString(R.string.menu_home)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        if (api == ParamAPI.CATEGORY_LIST) {
            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelCategoryList::class.java)
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    if (modelSuccess.details.size != 0) {
                        text_home.visibility = View.GONE
                        rcyHomeProductList.visibility = View.VISIBLE
                        //rcyHomeProductList.layoutManager = LinearLayoutManager(requireActivity())
                        rcyHomeProductList.layoutManager = GridLayoutManager(activity, 3)
                        rcyHomeProductList.adapter =
                            ItemProductList(requireActivity(), modelSuccess.details, this)
                        if (modelSuccess.details.size != 0) {
                            val adapter = SliderAdapterExample(requireActivity())
                            imageSlider.setSliderAdapter(adapter)
                            for (slider in modelSuccess.slider) {
                                val sliderItem = SliderItem(slider.image.toString())
                                adapter.addItem(sliderItem)
                            }
                            imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                            imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
                            imageSlider.indicatorSelectedColor = Color.WHITE
                            imageSlider.indicatorUnselectedColor = Color.TRANSPARENT
                            imageSlider.scrollTimeInSec = 2 //set scroll delay in seconds :
                            imageSlider.startAutoCycle()
                        }
                    } else {
                        text_home.visibility = View.VISIBLE
                        rcyHomeProductList.visibility = View.GONE
                    }
                } else {
                    text_home.visibility = View.VISIBLE
                    text_home.text = modelSuccess.message.toString()
                    rcyHomeProductList.visibility = View.GONE
                }
            } else {
                text_home.visibility = View.VISIBLE
                text_home.text = getString(R.string.something_went_wrong)
                rcyHomeProductList.visibility = View.GONE
            }
        } else {
            showServiceListDialog()
        }
    }

    private fun showServiceListDialog(/*model: ModelSuccess?*/) {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.dialog_service_list)
        val rcyServiceList = dialog.findViewById<RecyclerView>(R.id.rcyServiceList)
        val txtTitle = dialog.findViewById<TextView>(R.id.message_title_permission)
        txtTitle.text = "Select Booking"
        rcyServiceList.layoutManager = LinearLayoutManager(requireContext())
        val serviceList = ArrayList<ServiceModel>()
        var serviceModel = ServiceModel(R.drawable.ic_installation, "Installation")
        serviceList.add(serviceModel)
        serviceModel = ServiceModel(R.drawable.ic_un_installation, "Un Installation")
        serviceList.add(serviceModel)
        serviceModel = ServiceModel(R.drawable.ic_progress, "Service")
        serviceList.add(serviceModel)
        serviceModel = ServiceModel(R.drawable.ic_repair, "Repair")
        serviceList.add(serviceModel)
        rcyServiceList.adapter = ItemServiceSelection(requireActivity(), serviceList, dialog)
        dialog.show()
    }

    override fun onResponseFailure(message: String, api: String) {
        if (api == SERVICE_TYPE_LIST) {
            showServiceListDialog()
            showSnackView(message, snackViewHome)
        } else {
            text_home.visibility = View.VISIBLE
            text_home.text = message
            rcyHomeProductList.visibility = View.GONE
        }
    }

    override fun productClick(position: Int, categoryId: String) {
        showServiceListDialog()
        /*val hashMap = HashMap<String, String>()
        hashMap[ParamKey.USER_ID] = SessionManager.getObject(requireActivity()).id.toString()
        hashMap[ParamKey.CATEGORY_ID] = categoryId
        hashMap[ParamKey.CATEGORY_NAME] = categoryName
        APIConnector.callBasic(requireActivity(), hashMap, this, ParamAPI.SERVICE_TYPE_LIST)*/
    }

    class ItemServiceSelection(
        val activity: FragmentActivity,
        val listOfServices: ArrayList<ServiceModel>,
        val dialog: Dialog
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
            // holder.imgServiceLogo.setImageResource(listOfServices[position].img)
            holder.txtServiceName.setText(listOfServices[position].serviceName)

            when (listOfServices[position].serviceName) {
                listOfServices[0].serviceName -> {
                    holder.imgServiceLogo.setImageDrawable(activity.getDrawable(R.drawable.ic_installation))
                }
                listOfServices[1].serviceName -> {
                    holder.imgServiceLogo.setImageDrawable(activity.getDrawable(R.drawable.ic_service))
                }
                listOfServices[2].serviceName -> {
                    holder.imgServiceLogo.setImageDrawable(activity.getDrawable(R.drawable.ic_uninstalla))
                }
                listOfServices[3].serviceName -> {
                    holder.imgServiceLogo.setImageDrawable(activity.getDrawable(R.drawable.ic_repair))
                }
            }
            holder.layOfServiceSelection.setOnClickListener {
                MessageUtils.dismissDialog(dialog)
                val bundle = Bundle()
                bundle.putString("id", categoryId)
                bundle.putString("type", listOfServices[position].serviceName)
                MainActivity.navController.navigate(
                    R.id.action_sub_category_from_product_list,
                    bundle
                )
            }
        }
    }

    class ServiceHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        val imgServiceLogo = itemVIew.imgServiceLogo
        val txtServiceName = itemVIew.txtServiceName
        val layOfServiceSelection = itemVIew.layOfServiceSelection
    }

    data class ServiceModel(val img: Int, val serviceName: String)
}
