package com.sungkunn.inam.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sungkunn.inam.ManageActivity
import com.sungkunn.inam.R
import com.sungkunn.inam.view.MenuCardView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*

class Home : AppCompatActivity(), MenuCardView.MenuClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        menu_north.setMenuClickListener(this, "north")
        menu_east.setMenuClickListener(this, "east")
        menu_west.setMenuClickListener(this, "west")
        menu_south.setMenuClickListener(this, "south")
        menu_northeast.setMenuClickListener(this, "northeast")

        menu_market.setMenuClickListener(this, "market")
        menu_zone.setMenuClickListener(this, "zone")
        menu_store.setMenuClickListener(this, "store")
        menu_product.setMenuClickListener(this, "product")
        menu_stock.setMenuClickListener(this, "stock")

        menu_hotel.setMenuClickListener(this, "hotel")
        menu_room.setMenuClickListener(this, "room")
        menu_book.setMenuClickListener(this, "book")

        menu_travel.setMenuClickListener(this, "travel")
        menu_network.setMenuClickListener(this, "network")
        menu_service.setMenuClickListener(this, "service")

    }

    override fun onPhotoClick(v: View?, tag: String?) {
        Log.d("Home", "Menu click " + tag)
        if (tag.equals("north") || tag.equals("east") || tag.equals("west") ||
                tag.equals("south") || tag.equals("northeast")) {
            val intent = Intent(this@Home, HomeRegionActivity::class.java)
            intent.putExtra("region", tag)
            startActivity(intent)
        } else {
            val intent = Intent(this@Home, ManageActivity::class.java)
            intent.putExtra("category", tag)
            startActivity(intent)
        }

    }



}
