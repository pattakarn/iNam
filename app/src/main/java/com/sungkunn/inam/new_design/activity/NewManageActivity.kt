package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.ui.manage.NewManageFragment

class NewManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_manage_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewManageFragment.newInstance())
                .commitNow()
        }
    }

}
