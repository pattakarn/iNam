package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.CommunityDao
import com.sungkunn.inam.new_design.ui.show.ShowCommunityFragment

class ShowCommunityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_community_activity)

        var bundle = intent.extras
        var item: CommunityDao = bundle.getParcelable("item")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ShowCommunityFragment.newInstance(item))
                .commitNow()
        }
    }
}
