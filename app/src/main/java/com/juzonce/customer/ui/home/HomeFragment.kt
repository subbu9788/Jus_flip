package com.juzonce.customer.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.adapter.ItemProductList
import com.juzonce.customer.adapter.slider.SliderAdapterExample
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.ModelCategoryList
import com.juzonce.customer.model.sliderMenu.SliderItem
import com.juzonce.customer.utils.Utils
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.ResponseBody
import retrofit2.Response

class HomeFragment : BaseFragment(), ReadWriteAPI {

    companion object {
        var categoryName = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_home.visibility = View.GONE
        val map = Utils.getMapDefaultValues(requireActivity())
        APIConnector.callBasic(requireActivity(), map, this, ParamAPI.CATEGORY_LIST)
        rcyHomeProductList.layoutManager = GridLayoutManager(activity, 2)
        // showSliderAndListView()

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
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT;
        imageSlider.indicatorSelectedColor = Color.WHITE;
        imageSlider.indicatorUnselectedColor = Color.GRAY;
        imageSlider.scrollTimeInSec = 2; //set scroll delay in seconds :
        imageSlider.startAutoCycle();
    }

    override fun getTitle(): String {
        return getString(R.string.menu_home)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
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
                        ItemProductList(requireActivity(), modelSuccess.details)
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
                        imageSlider.indicatorUnselectedColor = Color.GRAY
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
    }

    override fun onResponseFailure(message: String, api: String) {
        text_home.visibility = View.VISIBLE
        text_home.text = message
        rcyHomeProductList.visibility = View.GONE
    }
}
