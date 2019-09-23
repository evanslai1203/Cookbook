package com.simpnic.fragmentfactory

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.simpnic.fragmentfactory.ui.DashboardFragment
import com.simpnic.fragmentfactory.ui.HomeFragment
import com.simpnic.fragmentfactory.ui.NotificationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val homeFragment: Fragment = HomeFragment.instance
    private val dashboardFragment: Fragment = DashboardFragment.instance
    private val notificationFragment: Fragment = NotificationFragment.instance
    private var usedFragment: Fragment = homeFragment

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                selectFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                selectFragment(dashboardFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                selectFragment(notificationFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun selectFragment(fragment: Fragment) {
        if (usedFragment == fragment) return
        supportFragmentManager.beginTransaction().show(fragment).hide(usedFragment).commitNow()
        usedFragment = fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate: ");

        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

//        supportFragmentManager.beginTransaction().add(R.id.container, homeFragment).show(homeFragment).commit()
//        supportFragmentManager.beginTransaction().add(R.id.container, dashboardFragment).hide(dashboardFragment).commit()
//        supportFragmentManager.beginTransaction().add(R.id.container, notificationFragment).hide(notificationFragment).commit()

        supportFragmentManager.beginTransaction().add(R.id.container, homeFragment).commit()
//        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().add(R.id.container, dashboardFragment).addToBackStack(null).commitNow()
//        supportFragmentManager.popBackStack()
        Log.d("MainActivity", "onCreate: " + supportFragmentManager.backStackEntryCount);
        supportFragmentManager.beginTransaction().replace(R.id.container, notificationFragment).addToBackStack(null).commit()
        Log.d("MainActivity", "onCreate: fragment size " + supportFragmentManager.fragments.size);
//        supportFragmentManager.popBackStack()
    }


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart: ");
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume: ");
        Log.d("MainActivity", "onResume: " + supportFragmentManager.backStackEntryCount);

    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause: ");
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop: ");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy: ");
    }


}
