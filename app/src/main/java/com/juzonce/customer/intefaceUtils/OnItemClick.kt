package com.juzonce.customer.intefaceUtils

import android.widget.AutoCompleteTextView

interface OnItemClick {

    /**
     * Call API Call after Select Dropdown or Filtered values base
     */
    fun onClick(
        position: Int,
        arrayList: ArrayList<String>,
        autoCompleteTextView: AutoCompleteTextView
    )
}