package com.sungkunn.inam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.ui.manage.ManageFragment

class ManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_activity)

        var category = intent.getStringExtra("category")
//        Log.d("ManageActivity", category)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_manage, ManageFragment.newInstance(category))
                .commitNow()

//            supportFragmentManager.beginTransaction()
//                .add(R.id.container, MySettingsSampleFragment())
//                .commitNow()
        }


    }




}
