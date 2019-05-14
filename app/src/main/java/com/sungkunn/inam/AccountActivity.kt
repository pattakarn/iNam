package com.sungkunn.inam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sungkunn.inam.ui.account.AccountFragment

class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AccountFragment.newInstance())
                .commitNow()
        }
    }

}
