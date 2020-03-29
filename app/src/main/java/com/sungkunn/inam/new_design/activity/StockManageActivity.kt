package com.sungkunn.inam.new_design.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.new_design.model.ProductDao
import com.sungkunn.inam.new_design.ui.manage.stock.StockManageFragment

class StockManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_manage_activity)

        var bundle = intent.extras
        var item: ProductDao? = null

        if (bundle != null){
            item = bundle.getParcelable("item")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, StockManageFragment.newInstance(item))
                .commitNow()
        }
    }
}
