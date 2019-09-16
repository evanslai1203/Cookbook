package com.simpnic.videoviewwithmute

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.media.MediaPlayer


class MainActivity : AppCompatActivity() {

    var mute = false
    var m: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView.setOnPreparedListener {
            m = it
        }
        videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.simpnic_45s)

        videoView.requestFocus()
        videoView.setOnClickListener {
            mute = if (mute) {
                m?.setVolume(1f,1f)
                false
            } else {
                m?.setVolume(0f,0f)
                true
            }
        }

        videoView.start()
    }
}
