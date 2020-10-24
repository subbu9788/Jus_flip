package com.juzonce.customer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.juzonce.customer.ui.splash.SplashFragment
import com.juzonce.customer.utils.MessageUtils
import com.juzonce.customer.utils.UiUtils
import com.juzonce.customer.utils.toolbar.FragmentListener

abstract class BaseFragment : Fragment(), UiUtils {

    private var fragmentListener: FragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentListener)
            fragmentListener = context
        else
            throw RuntimeException("$context must implement FragmentListener")

        if (fragmentListener != null)
            fragmentListener?.setShowHomeViews(getShowHomeToolbar())
        else
            Log.i("showHomeViews", "showHomeViews null")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }

    override fun onResume() {
        super.onResume()
        Log.i("showHomeViews", "showHomeViews onResume")
        if (fragmentListener != null) {
            fragmentListener?.setTitle(getTitle())
            Log.i("showHomeViews", "showHomeViews onResume fragmentListener")
            fragmentListener?.setShowHomeViews(getShowHomeToolbar())
        } else
            Log.i("showHomeViews", "showHomeViews onResume fragmentListener null")
    }

    protected abstract fun getTitle(): String

    protected abstract fun getShowHomeToolbar(): Boolean

    override fun showSnackView(message: String, view: View) {
        SplashFragment.snackbar = MessageUtils.showSnackBar(activity, view, message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MessageUtils.dismissSnackBar(SplashFragment.snackbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        MessageUtils.dismissSnackBar(SplashFragment.snackbar)
    }
}