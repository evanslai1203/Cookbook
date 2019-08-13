package com.evanslai.handlerleak

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    lateinit var handler:MyHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val outerClass = WeakReference(this)
        handler = MyHandler(outerClass)

    }

    override fun onResume() {
        super.onResume()
        handler.sendMessage(handler.obtainMessage(0,"hi"))
    }

    override fun onDestroy() {
        super.onDestroy()
        // remove all Message form MessageQueue
        handler.removeCallbacksAndMessages(null)
    }

}

// Declare the Handler as a static class.
class MyHandler(private val outerClass: WeakReference<MainActivity>) : Handler() {
    override fun handleMessage(msg: Message?) {
        outerClass.get()?.textView?.text = msg?.obj.toString()
    }
}
