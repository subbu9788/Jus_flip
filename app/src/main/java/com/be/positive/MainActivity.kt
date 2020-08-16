package com.be.positive

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.telephony.MbmsDownloadSession.RESULT_CANCELLED
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.be.positive.utils.ImageUtils
import com.be.positive.utils.SessionManager
import com.be.positive.utils.toolbar.FragmentListener
import com.google.android.material.navigation.NavigationView
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : BaseActivity(), FragmentListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        lateinit var drawerLayout: DrawerLayout
        lateinit var navView: NavigationView
        //var bottomNavigation: BottomNavigationView? = null
        lateinit var navController: NavController
        lateinit var sessionManager: SessionManager
        lateinit var dialog: Dialog

        fun setNavigationTitle(activity: FragmentActivity) {
            sessionManager = SessionManager(activity)
            val view: View = navView.getHeaderView(0)
            val imageView = view.imageView
            val headerTitle = view.headerTitle
            val textView = view.textView
            if (sessionManager.isLoggedIn) {
                headerTitle.text = SessionManager.getObject(activity).name
                textView.text = SessionManager.getObject(activity).email
                ImageUtils.setImageLive(imageView, "llasdjklkf", activity)
            } else {
                headerTitle.text = "JustFlip"
                textView.text = "test@gmail.com"
            }
        }
    }

    lateinit var toggle: ActionBarDrawerToggle
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val contents = data!!.getStringExtra("SCAN_RESULT")
                Log.d("scannerResult", "yes " + contents)
            }
            if (resultCode == RESULT_CANCELLED) { //handle cancel
                Log.d("scannerResult", "not")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        /*try {
             val intent = Intent("com.google.zxing.client.android.SCAN")
                 intent.putExtra("SCAN_MODE", "QR_CODE_MODE") // "PRODUCT_MODE for bar codes
                 startActivityForResult(intent, 0)
        } catch (ex: Exception) {
            ex.printStackTrace()
            val marketUri: Uri = Uri.parse("market://details?id=com.google.zxing.client.android")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            startActivity(marketIntent)
        }*/
        drawerLayout = findViewById(
            R.id.drawer_layout
        )
        navView = findViewById(
            R.id.nav_view
        )
        navController = findNavController(
            R.id.nav_host_fragment
        )
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_msg, R.string.close_msg
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = true
        setupActionBarWithNavController(navController, appBarConfiguration)

        setNavigationTitle(this)

        navView.setupWithNavController(
            navController
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun navigationHide() {
        supportActionBar!!.hide()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun disableNavigation(title: String) {
        supportActionBar!!.show()
        toggle.isDrawerIndicatorEnabled = false
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(false)
        toggle.setToolbarNavigationClickListener {
            onBackPressed()
        }
    }


    fun enableNavigation(titleName: String, position: Int) {
        supportActionBar!!.show()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        supportActionBar!!.setHomeButtonEnabled(true)
        toggle.isDrawerIndicatorEnabled = true
        supportActionBar!!.title = titleName
        navView.menu.getItem(position)?.isChecked = true
    }

    override fun setTitle(titleString: String) {
        toolbar.title = titleString
        //title = titleString
    }

    override fun setShowHomeViews(home: Boolean) {
        Log.d("homesds", "" + home)
    }

}
