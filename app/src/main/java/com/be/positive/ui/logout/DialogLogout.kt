package com.rcd.driver.ui.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.be.positive.utils.SessionManager
import com.kirana.merchant.R


import kotlinx.android.synthetic.main.dialog_logout.*

import java.lang.Exception

class DialogLogout : DialogFragment() {

    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_logout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireActivity())
        btnCancelInHorizontal.text = requireActivity().getString(R.string.no)
        btnSubmitInHorizontal.text = requireActivity().getString(R.string.yes)

        btnCancelInHorizontal.setOnClickListener {
            try {
                findNavController().popBackStack()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        btnSubmitInHorizontal.setOnClickListener {
            SessionManager.logoutUser(requireActivity())
        }
    }

}