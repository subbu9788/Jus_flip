package com.juzonce.customer.ui.productModel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.adapter.modelList.ItemProductModel
import com.juzonce.customer.adapter.pagerAdapter.PagerAdapter
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ParamKey
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.DetailsItemBrand
import com.juzonce.customer.model.DetailsItemModel
import com.juzonce.customer.model.PojoBrand
import com.juzonce.customer.model.PojoModels
import com.juzonce.customer.ui.home.HomeFragment.Companion.categoryName
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.NonSwipeableViewPager
import com.juzonce.customer.utils.Utils
import kotlinx.android.synthetic.main.fragment_product_model.*
import okhttp3.ResponseBody
import retrofit2.Response

class ProductModelFragment : BaseFragment(), ReadWriteAPI {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    var details: List<DetailsItemBrand> = ArrayList()
    lateinit var readWriteAPI: ReadWriteAPI
    var time = ""
    var tabPosition = 0

    companion object {
        var categoryId = ""
        var brand_id = ""
        var brandName = ""
        var modelName = ""
        var type = ""
        var detailsModels: ArrayList<DetailsItemModel> = ArrayList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readWriteAPI = this

        categoryId = requireArguments().getString("id").toString()
        type = requireArguments().getString("type").toString()
        val map = Utils.getMapDefaultValues(requireActivity())
        map[ParamKey.CATEGORY_ID] = "" + categoryId
        map[ParamKey.TYPE] = "" + type
        Log.d("mapsd", "" + map)
        //APIConnector.callBasic(requireActivity(), map, this, ParamAPI.BRAND_LIST)
        btnJuzBook.animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.shake)
        tabLayout = view.findViewById(R.id.tab_layout) as TabLayout
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        viewPager = view.findViewById(R.id.pager) as NonSwipeableViewPager

        edtServiceType.setText(type)
        edtCategoryName.setText(categoryName)

        edtVisitDate.setOnClickListener {
            Utils.getDateFromDatePicker(requireActivity(), edtVisitDate)
        }
        btnJuzBook.setOnClickListener {
            try {
                val map = Utils.getMapDefaultValues(requireActivity())
                if (edtVisitDate.text.toString().isNotEmpty()) {
                    if (time.isNotEmpty()) {
                        map[ParamKey.DATE] = edtVisitDate.text.toString()
                        map[ParamKey.TIME] = time
                        map[ParamKey.CATEGORY_NAME] = categoryName
                        map[ParamKey.CATEGORY_ID] = categoryId
                        map[ParamKey.MODEL_NAME] = edtModelName.text.toString()
                        map[ParamKey.BRAND_NAME] = edtBrandName.text.toString()
                        map[ParamKey.REASON] = edtVisitReason.text.toString()
                        map[ParamKey.LAND_MARK] = edtLandmark.text.toString()
                        map[ParamKey.ADDRESS] = edtAddress.text.toString()
                        map[ParamKey.REASON] = edtRemarks.text.toString()
                        map[ParamKey.TYPE] = type
                        APIConnector.callBasic(requireActivity(), map, readWriteAPI, ParamAPI.BOOK)

                    } else {
                        showSnackView("Select Visiting Time", snackViewInBookingPage)
                    }
                } else {
                    showSnackView("Select Visiting Date", snackViewInBookingPage)
                }
            } catch (ex: Exception) {
            }
        }

        btnFirst.setOnClickListener {
            time = btnFirst.text.toString()
            btnFirst.setBackgroundResource(R.drawable.btn_shape_capsule_theme_base)
            btnSecond.setBackgroundResource(R.drawable.btn_shape_capsule_white)
            btnThird.setBackgroundResource(R.drawable.btn_shape_capsule_white)
            btnFirst.setTextColor(requireActivity().resources.getColor(R.color.colorWhite))
            btnSecond.setTextColor(requireActivity().resources.getColor(R.color.colorBlack))
            btnThird.setTextColor(requireActivity().resources.getColor(R.color.colorBlack))
        }

        btnSecond.setOnClickListener {
            time = btnSecond.text.toString()
            btnFirst.setBackgroundResource(R.drawable.btn_shape_capsule_white)
            btnSecond.setBackgroundResource(R.drawable.btn_shape_capsule_theme_base)
            btnThird.setBackgroundResource(R.drawable.btn_shape_capsule_white)
            btnFirst.setTextColor(requireActivity().resources.getColor(R.color.colorBlack))
            btnSecond.setTextColor(requireActivity().resources.getColor(R.color.colorWhite))
            btnThird.setTextColor(requireActivity().resources.getColor(R.color.colorBlack))
        }

        btnThird.setOnClickListener {
            time = btnThird.text.toString()
            btnFirst.setBackgroundResource(R.drawable.btn_shape_capsule_white)
            btnSecond.setBackgroundResource(R.drawable.btn_shape_capsule_white)
            btnThird.setBackgroundResource(R.drawable.btn_shape_capsule_theme_base)
            btnFirst.setTextColor(requireActivity().resources.getColor(R.color.colorBlack))
            btnSecond.setTextColor(requireActivity().resources.getColor(R.color.colorBlack))
            btnThird.setTextColor(requireActivity().resources.getColor(R.color.colorWhite))
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                brandName = p0!!.text.toString()
                tabPosition = p0!!.position
                viewPager.currentItem = tabPosition

                for (index in 0 until details.size) {
                    if (p0!!.text.toString() == details[index].brandName.toString()) {
                        brand_id = details[index].id.toString()
                    }
                }
                val map = Utils.getMapDefaultValues(requireActivity())
                map[ParamKey.CATEGORY_ID] = categoryId
                map[ParamKey.BRAND_ID] = brand_id
                APIConnector.callBasic(
                    requireActivity(),
                    map,
                    readWriteAPI,
                    ParamAPI.MODEL_LIST
                )
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).disableNavigation(categoryName)
        return inflater.inflate(R.layout.fragment_product_model, container, false)
    }

    override fun getTitle(): String {
        return getString(R.string.booking)//categoryName
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailsModels.clear()
    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            if (ParamAPI.MODEL_LIST == api) {
                val modelSuccess =
                    Gson().fromJson(responseBody.body()?.string(), PojoModels::class.java)
                if (modelSuccess != null) {
                    if (modelSuccess.status!!) {
                        if (modelSuccess.details.size != 0) {
                            detailsModels = modelSuccess.details
                            rcyViewOfModel.visibility = View.VISIBLE
                            pagerNo.visibility = View.GONE
                            viewPager.currentItem = tabPosition
                            rcyViewOfModel.layoutManager =
                                LinearLayoutManager(requireActivity())
                            rcyViewOfModel.adapter =
                                ItemProductModel(requireActivity(), detailsModels)

                        } else {
                            rcyViewOfModel.visibility = View.GONE
                            pagerNo.visibility = View.VISIBLE
                            pagerNo.text = "Brand Models Not found"
                        }

                    } else {
                        rcyViewOfModel.visibility = View.GONE
                        pagerNo.visibility = View.VISIBLE
                        pagerNo.text = modelSuccess.message.toString()
                    }
                } else {
                    rcyViewOfModel.visibility = View.GONE
                    pagerNo.visibility = View.VISIBLE
                    pagerNo.text = getString(R.string.something_went_wrong)
                }
            } else if (api == ParamAPI.BOOK) {
                val modelSuccess =
                    Gson().fromJson(responseBody.body()?.string(), PojoBrand::class.java)

                if (modelSuccess != null) {
                    if (modelSuccess.status!!) {
                        MessageUtils.showToastMessageLong(
                            requireActivity(),
                            modelSuccess.message!!
                        )
                        findNavController().popBackStack()
                    } else {
                        showSnackView(
                            modelSuccess.message!!,
                            snackViewInBookingPage
                        )
                    }
                } else {
                    showSnackView(
                        getString(R.string.something_went_wrong),
                        snackViewInBookingPage
                    )
                }
            } else {
                val modelSuccess =
                    Gson().fromJson(responseBody.body()?.string(), PojoBrand::class.java)
                if (modelSuccess != null) {
                    if (modelSuccess.status!!) {
                        if (modelSuccess.details.size != 0) {
                            details = modelSuccess.details
                            for (item in modelSuccess.details) {
                                tabLayout.addTab(
                                    tabLayout.newTab().setText("" + item.brandName)
                                )
                            }

                            val adapter = PagerAdapter(childFragmentManager, tabLayout.tabCount)
                            viewPager.adapter = adapter
                            viewPager.currentItem = 0

                            if (tabLayout.tabCount == 2) {
                                tabLayout.tabMode = TabLayout.MODE_FIXED
                            } else {
                                tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
                            }
                            brand_id = details[0].id.toString()
                            val map = Utils.getMapDefaultValues(requireActivity())
                            map[ParamKey.CATEGORY_ID] = categoryId
                            map[ParamKey.BRAND_ID] = brand_id
                            APIConnector.callBasic(
                                requireActivity(),
                                map,
                                readWriteAPI,
                                ParamAPI.MODEL_LIST
                            )

                        }
                    } else {
                        text_gallery.visibility = View.VISIBLE
                        text_gallery.text = modelSuccess.message.toString()
                        tab_layout.visibility = View.GONE
                        rcyViewOfModel.visibility = View.GONE
                    }
                } else {
                    text_gallery.text = getString(R.string.something_went_wrong)
                    text_gallery.visibility = View.VISIBLE
                    tab_layout.visibility = View.GONE
                    rcyViewOfModel.visibility = View.GONE
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        if (ParamAPI.MODEL_LIST == api) {
            rcyViewOfModel.visibility = View.GONE
            pagerNo.visibility = View.VISIBLE
            pagerNo.text = message
        } else if (api == ParamAPI.BOOK) {
            showSnackView(message, snackViewInBookingPage)
        } else {

            text_gallery.text = message
            text_gallery.visibility = View.VISIBLE
            tab_layout.visibility = View.GONE
            rcyViewOfModel.visibility = View.GONE
        }
    }

}
