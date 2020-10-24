package com.juzonce.customer.ui.productViewAndBookService

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.juzonce.customer.BaseFragment
import com.juzonce.customer.MainActivity
import com.juzonce.customer.R
import com.juzonce.customer.api.APIConnector
import com.juzonce.customer.api.ParamAPI
import com.juzonce.customer.api.ParamKey
import com.juzonce.customer.api.ReadWriteAPI
import com.juzonce.customer.model.DetailsItemModel
import com.juzonce.customer.model.ModelSuccess
import com.juzonce.customer.ui.productModel.ProductModelFragment
import com.juzonce.customer.ui.productModel.ProductModelFragment.Companion.brand_id
import com.juzonce.customer.ui.productModel.ProductModelFragment.Companion.categoryId
import com.juzonce.customer.utils.ImageUtils
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.Utils
import com.juzonce.customer.utils.Validator
import kotlinx.android.synthetic.main.fragment_product_view_and_book_service.*
import okhttp3.ResponseBody
import retrofit2.Response


class ProductViewAndBookServiceFragment : BaseFragment(), ReadWriteAPI {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = requireArguments().getSerializable("items") as DetailsItemModel
        txtBrandName.text = ProductModelFragment.brandName
        txtModelName.text = ProductModelFragment.modelName
        ImageUtils.setImageLive(modelImage, items.modelImage.toString(), activity)

        btnRequest.animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.shake)

        btnRequest.setOnClickListener {
            try {
                val map = Utils.getMapDefaultValues(requireActivity())
                map[ParamKey.MODEL_ID] = items.modelId.toString()
                map[ParamKey.MODEL_NAME] = items.modelName.toString()
                map[ParamKey.BRAND_ID] = brand_id
                map[ParamKey.CATEGORY_ID] = categoryId
                map[ParamKey.DATE] = edtVisitDate.text.toString()
                map[ParamKey.TIME] = edtVisitTime.text.toString()
                map[ParamKey.REASON] = edtReason.text.toString()
                if (Validator.validateBook(map, requireActivity(), detaliedSnackView)) {
                    APIConnector.callBasic(requireActivity(), map, this, ParamAPI.BOOK)
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        edtVisitDate.setOnClickListener {
            Utils.getDateFromDatePicker(requireActivity(), edtVisitDate)
        }
        edtVisitTime.setOnClickListener {
            Utils.getTimeFromTimePicker(requireActivity(), edtVisitTime)

        }
    }

    override fun getTitle(): String {
        return getString(R.string.booking)
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).disableNavigation(getString(R.string.booking))
        return inflater.inflate(R.layout.fragment_product_view_and_book_service, container, false)

    }

    override fun onResponseSuccess(responseBody: Response<ResponseBody>, api: String) {
        try {
            val modelSuccess =
                Gson().fromJson(responseBody.body()?.string(), ModelSuccess::class.java)
            if (modelSuccess != null) {
                if (modelSuccess.status!!) {
                    findNavController().popBackStack()
                    MessageUtils.showToastMessageLong(
                        requireActivity(),
                        modelSuccess.message.toString()
                    )
                } else {
                    showSnackView(modelSuccess.message.toString(), detaliedSnackView)
                }
            } else {
                showSnackView(getString(R.string.something_went_wrong), detaliedSnackView)
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
    }

    override fun onResponseFailure(message: String, api: String) {
        showSnackView(message, detaliedSnackView)
    }
}
