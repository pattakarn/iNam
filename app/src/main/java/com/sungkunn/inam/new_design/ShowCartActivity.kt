package com.sungkunn.inam.new_design

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.ui.show.ShowCartFragment

class ShowCartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_cart_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ShowCartFragment.newInstance())
                .commitNow()
        }
    }
}
