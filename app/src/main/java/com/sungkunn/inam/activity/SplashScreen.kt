package com.sungkunn.inam.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.activity.NewMainActivity


class SplashScreen : AppCompatActivity() {

    var handler = null
    var runnable = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        handler = Handler()

        Thread(Runnable {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
            }

            val intent = Intent(this@SplashScreen, NewMainActivity::class.java)
            startActivity(intent)
            finish()
        }).start()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }
}
