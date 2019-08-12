package com.evanslai.animatedfabwithoptions

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isRotate = false

        fabAdd.setOnClickListener { view ->
            isRotate = ViewAnimation.rotateFab(view, !isRotate)
                    if (isRotate) {
                        ViewAnimation.showIn(fabCall)
                        ViewAnimation.showIn(fabInfo)
                    } else {
                        ViewAnimation.showOut(fabCall)
                        ViewAnimation.showOut(fabInfo)
                    }

                }

        ViewAnimation.init(fabInfo)
        ViewAnimation.init(fabCall)

        fabCall.setOnClickListener {
            Toast.makeText(this,"Calling",Toast.LENGTH_SHORT).show()
        }

        fabInfo.setOnClickListener {
            Toast.makeText(this,"Info",Toast.LENGTH_SHORT).show()
        }

    }
}
