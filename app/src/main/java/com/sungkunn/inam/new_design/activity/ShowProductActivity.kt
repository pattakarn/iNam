package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.model.ProductPackDao
import com.sungkunn.inam.new_design.ui.show.ShowProductFragment

class ShowProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_product_activity)

        var bundle = intent.extras
        var item: ProductPackDao = bundle.getParcelable("item")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ShowProductFragment.newInstance(item))
                .commitNow()
        }
    }
}
