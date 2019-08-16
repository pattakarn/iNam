package com.sungkunn.inam.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.fragment.ui.hotel.HotelMainFragment
import com.sungkunn.inam.fragment.ui.network.NetworkMainFragment
import com.sungkunn.inam.fragment.ui.product.ProductMainFragment
import com.sungkunn.inam.fragment.ui.service.ServiceMainFragment
import com.sungkunn.inam.fragment.ui.shop.ShopMainFragment
import com.sungkunn.inam.fragment.ui.travel.TravelMainFragment

class UIItemActivity : AppCompatActivity() {

    var key: String? = null
    var name: String? = null
    var type: String? = null

    var TAG = "UIItemActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uiitem)

        key = intent.extras.getString("key")
        name = intent.extras.getString("name")
        type = intent.extras.getString("type")
        Log.d(TAG, " ============= " + type)
        if (savedInstanceState == null) {
            if (type.equals("shop")){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_uiitem, ShopMainFragment.newInstance(key, name, type))
                    .commitNow()
            } else if(type.equals("product")){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_uiitem, ProductMainFragment.newInstance(key, name, type))
                    .commitNow()
            } else if(type.equals("hotel")){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_uiitem, HotelMainFragment.newInstance(key, name, type))
                    .commitNow()
            } else if(type.equals("network")){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_uiitem, NetworkMainFragment.newInstance(key, name, type))
                    .commitNow()
            } else if(type.equals("service")){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_uiitem, ServiceMainFragment.newInstance(key, name, type))
                    .commitNow()
            } else if(type.equals("travel")){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_uiitem, TravelMainFragment.newInstance(key, name, type))
                    .commitNow()
            }


//            supportFragmentManager.beginTransaction()
//                .add(R.id.container, MySettingsSampleFragment())
//                .commitNow()
        }
    }
}
