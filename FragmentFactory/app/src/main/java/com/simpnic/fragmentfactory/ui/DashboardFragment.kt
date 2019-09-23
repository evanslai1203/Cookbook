package com.simpnic.fragmentfactory.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.simpnic.fragmentfactory.R

class DashboardFragment : Fragment(){

    companion object {
        val instance: DashboardFragment by lazy { DashboardFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("DashboardFragment", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("DashboardFragment", "onViewCreated: ");
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("DashboardFragment", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DashboardFragment", "onCreate: ");
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("DashboardFragment", "onActivityCreated: ");
    }

    override fun onStart() {
        super.onStart()
        Log.d("DashboardFragment", "onStart: ");
    }

    override fun onResume() {
        super.onResume()
        Log.d("DashboardFragment", "onResume: ");
    }

    override fun onPause() {
        super.onPause()
        Log.d("DashboardFragment", "onPause: ");
    }

    override fun onStop() {
        super.onStop()
        Log.d("DashboardFragment", "onStop: ");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("DashboardFragment", "onDestroyView: ");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DashboardFragment", "onDestroy: ");
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("DashboardFragment", "onDetach: ");
    }
}