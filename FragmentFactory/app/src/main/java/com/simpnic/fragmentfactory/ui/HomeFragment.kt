package com.simpnic.fragmentfactory.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.simpnic.fragmentfactory.R

class HomeFragment : Fragment(){

    companion object {
        val instance: HomeFragment by lazy { HomeFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("HomeFragment", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HomeFragment", "onViewCreated: ");
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("HomeFragment", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeFragment", "onCreate: ");
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("HomeFragment", "onActivityCreated: ");
    }

    override fun onStart() {
        super.onStart()
        Log.d("HomeFragment", "onStart: ");
    }

    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume: ");
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeFragment", "onPause: ");
    }

    override fun onStop() {
        super.onStop()
        Log.d("HomeFragment", "onStop: ");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("HomeFragment", "onDestroyView: ");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeFragment", "onDestroy: ");
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("HomeFragment", "onDetach: ");
    }
}