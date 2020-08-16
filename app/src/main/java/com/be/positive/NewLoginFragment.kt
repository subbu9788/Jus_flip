package com.be.positive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kirana.merchant.R

class NewLoginFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return layoutInflater.inflate(R.layout.activity_new_login, container, false)
    }

    override fun getTitle(): String {
        return "New Login"
    }

    override fun getShowHomeToolbar(): Boolean {
        return false
    }

}
