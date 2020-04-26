package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.model.PlacePackDao
import com.sungkunn.inam.new_design.ui.show.ShowPlaceFragment

class ShowPlaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_place_activity)

        var bundle = intent.extras
        var item: PlacePackDao = bundle.getParcelable("item")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ShowPlaceFragment.newInstance(item))
                .commitNow()
        }
    }

}
