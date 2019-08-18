package com.evanslai.eventbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(MessageEvent("要發送的訊息"))
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    // 註冊Subscribe，觀察目標為MessageEvent
    // Thread mode 有 ASYN, MAIN, POSTING, BACKGROUND 三種選擇，主線程才能修改UI，因此這裡選 MAIN
    // 這 onMessageEvent 方法，當有人呼叫 EventBus.getDefault().post(Object event)方法時，就會觸發，並把附帶的資料傳入
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        println(event.message)
    }
}
