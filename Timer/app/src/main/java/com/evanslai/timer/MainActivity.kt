package com.evanslai.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference
import androidx.core.os.HandlerCompat.postDelayed
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var handler: MyHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val outerClass = WeakReference(this)
        handler = MyHandler(outerClass)

        // Method 1: Timer, prefer this one
        useTimer()
        // Method 2: Use sleep
//        useSleep()
        // Method 2: Use postDelay
//        usePostDelay()

    }

    private fun useTimer() {
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val message = Message()
                message.what = 1
                handler.sendMessage(message)
            }
        }

        //start timer
        timer.schedule(task, 2000, 2000)
        // stop timer
//        timer.cancel()
    }

    private fun useSleep() {
        Thread {
            while (true) {
                Thread.sleep(2000)
                val message = Message()
                message.what = 1
                handler.sendMessage(message)
            }
        }.start()
    }

    //
    private fun usePostDelay() {
        val runnable = object : Runnable {
            override fun run() {
                val message = Message()
                message.what = 1
                handler.sendMessage(message)
                handler.postDelayed(this, 2000)
            }
        }

        //start timer
        handler.postDelayed(runnable, 2000)
        // stop timer
//        handler.removeCallbacks(runnable)
    }


    // Declare the Handler as a static class.
    class MyHandler(private val outerClass: WeakReference<MainActivity>) : Handler() {
        override fun handleMessage(msg: Message?) {
            println(msg?.what)
        }
    }
}
