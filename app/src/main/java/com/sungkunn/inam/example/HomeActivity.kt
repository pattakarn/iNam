package com.sungkunn.inam.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.fragment.home.HomeMarketFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_market)

        var category = intent.getStringExtra("category")
//        Log.d("ManageActivity", category)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_home, HomeMarketFragment.newInstance(category))
                .commitNow()

//            supportFragmentManager.beginTransaction()
//                .add(R.id.container, MySettingsSampleFragment())
//                .commitNow()
        }
    }
}
