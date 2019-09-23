package com.simpnic.fragmentfactory.ui

import androidx.fragment.app.Fragment

class FragmentFactory {

    val PAGE_HOME: Int = 0x10
    val PAGE_DASHBOARD: Int = 0x11
    val PAGE_NOTIFICATION: Int = 0x12

    fun createFragment(pageId: Int): Fragment {
        return when(pageId) {
            PAGE_HOME -> HomeFragment()
            PAGE_DASHBOARD -> DashboardFragment()
            PAGE_NOTIFICATION -> NotificationFragment()
            else -> Fragment()
        }
    }
}