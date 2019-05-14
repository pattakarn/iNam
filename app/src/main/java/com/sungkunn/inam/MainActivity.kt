package com.sungkunn.inam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.fragment.TestFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TestFragment.newInstance("", ""))
                .commitNow()

//            supportFragmentManager.beginTransaction()
//                .add(R.id.container, MySettingsSampleFragment())
//                .commitNow()
        }

    }
}
