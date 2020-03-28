package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.CommunityDao
import com.sungkunn.inam.new_design.ui.manage.community.NewEditCommunityFragment

class NewEditCommunityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_edit_community_activity)

        var bundle = intent.extras
        var item: CommunityDao? = null

        if (bundle != null){
            item = bundle.getParcelable("item")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewEditCommunityFragment.newInstance(item))
                .commitNow()
        }
    }
}
