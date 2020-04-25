package com.sungkunn.inam.new_design.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.PlaceDao
import com.sungkunn.inam.new_design.ui.manage.photo.NewEditPhotoFragment

class NewEditPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_edit_photo_activity)

//        var bundle = intent.extras
//        var item: Any? = null
        var id = intent.extras.getString("id")
        var name = intent.extras.getString("name")

//        if (bundle != null){
//            item = bundle.getParcelable("item")
//        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NewEditPhotoFragment.newInstance(id, name))
                .commitNow()
        }
    }
}
