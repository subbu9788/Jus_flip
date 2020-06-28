package com.be.positive

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import com.be.positive.utils.SessionManager
import com.be.positive.utils.toolbar.FragmentListener
import com.kirana.merchant.R
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(), FragmentListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        lateinit var drawerLayout: DrawerLayout
        lateinit var navView: NavigationView
        //var bottomNavigation: BottomNavigationView? = null
        lateinit var navController: NavController
        lateinit var sessionManager: SessionManager
        lateinit var dialog: Dialog
    }

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        setupActionBarWithNavController(navController, appBarConfiguration)
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
        drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun disableNavigation(title: String) {
        supportActionBar!!.show()
        toggle.isDrawerIndicatorEnabled = false
        drawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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
