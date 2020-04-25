package com.sungkunn.inam.new_design.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.UserDao
import com.sungkunn.inam.new_design.ui.manage.user.NewAccountFragment

class NewAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_account_activity)

        var bundle = intent.extras
        var item: UserDao? = null

        if (bundle != null){
            item = bundle.getParcelable("item")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewAccountFragment.newInstance(item))
                .commitNow()
        }
    }
}
