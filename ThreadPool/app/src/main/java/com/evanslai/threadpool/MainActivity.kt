package com.evanslai.threadpool

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.shutdown
import android.os.AsyncTask.execute
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. SingleThreadExecutor
        val pool = Executors.newSingleThreadExecutor()

        // 2. FixedThreadPool
        val cpuNumbers = Runtime.getRuntime().availableProcessors()
//        val pool = Executors.newFixedThreadPool(cpuNumbers * 3)

        // 3. CachedThreadPoo
//        val pool = Executors.newCachedThreadPool()


        val t1 = MyThread()
        val t2 = MyThread()
        val t3 = MyThread()
        val t4 = MyThread()
        val t5 = MyThread()

        pool.execute(t1)
        pool.execute(t2)
        pool.execute(t3)
        pool.execute(t4)
        pool.execute(t5)

        pool.shutdown()

        // 4. ScheduledThreadPoolExecutor
        val exec = ScheduledThreadPoolExecutor(1)
        exec.scheduleAtFixedRate({ println("===============") }, 1000, 5000, TimeUnit.MILLISECONDS)
        exec.scheduleAtFixedRate({ println(System.currentTimeMillis()) }, 1000, 2000, TimeUnit.MILLISECONDS)

    }
}

class MyThread : Thread() {
    override fun run() {
        println(Thread.currentThread().name + "is running")
    }
}
