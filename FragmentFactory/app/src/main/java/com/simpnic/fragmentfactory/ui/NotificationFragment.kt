package com.simpnic.fragmentfactory.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.simpnic.fragmentfactory.R

class NotificationFragment : Fragment(){

    companion object {
        val instance: NotificationFragment by lazy { NotificationFragment() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("NotificationFragment", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("NotificationFragment", "onViewCreated: ");
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("NotificationFragment", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("NotificationFragment", "onCreate: ");
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("NotificationFragment", "onActivityCreated: ");
    }

    override fun onStart() {
        super.onStart()
        Log.d("NotificationFragment", "onStart: ");
    }

    override fun onResume() {
        super.onResume()
        Log.d("NotificationFragment", "onResume: ");
    }

    override fun onPause() {
        super.onPause()
        Log.d("NotificationFragment", "onPause: ");
    }

    override fun onStop() {
        super.onStop()
        Log.d("NotificationFragment", "onStop: ");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("NotificationFragment", "onDestroyView: ");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("NotificationFragment", "onDestroy: ");
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("NotificationFragment", "onDetach: ");
    }
}