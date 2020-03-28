package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.ui.manage.place.NewEditPlaceFragment

class NewEditPlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_edit_place_activity)

        var bundle = intent.extras
        var item: PlaceDao? = null

        if (bundle != null){
            item = bundle.getParcelable("item")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewEditPlaceFragment.newInstance(item))
                .commitNow()
        }
    }

}
