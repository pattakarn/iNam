package com.sungkunn.inam.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.sungkunn.inam.R
import com.sungkunn.inam.view.MenuCardView

class TestFragment : Fragment(), MenuCardView.MenuClickListener {


    //    private var param1: String? = null
//    private var param2: String? = null
    var appBar: AppBarLayout? = null
    var toolbar: Toolbar? = null

    var menuNorth: MenuCardView? = null
    var menuEast: MenuCardView? = null
    var menuWest: MenuCardView? = null
    var menuSouth: MenuCardView? = null
    var menuNortheast: MenuCardView? = null

    var menuMarket: MenuCardView? = null
    var menuZone: MenuCardView? = null
    var menuStore: MenuCardView? = null
    var menuProduct: MenuCardView? = null
    var menuStock: MenuCardView? = null

    var menuHotel: MenuCardView? = null
    var menuRoom: MenuCardView? = null
    var menuBook: MenuCardView? = null

    var menuTravel: MenuCardView? = null
    var menuNetwork: MenuCardView? = null
    var menuService: MenuCardView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //            param1 = it.getString("")
//            param2 = it.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_test, container, false)
        init(rootView)

        return rootView
    }

    fun init(rootView: View?) {
        if (rootView != null) {
            appBar = rootView.findViewById(R.id.app_bar)
            toolbar = rootView.findViewById(R.id.toolbar)

            menuNorth = rootView.findViewById(R.id.menu_north)
            menuEast = rootView.findViewById(R.id.menu_east)
            menuWest = rootView.findViewById(R.id.menu_west)
            menuSouth = rootView.findViewById(R.id.menu_south)
            menuNortheast = rootView.findViewById(R.id.menu_northeast)

            menuMarket = rootView.findViewById(R.id.menu_market)
            menuZone = rootView.findViewById(R.id.menu_zone)
            menuStore = rootView.findViewById(R.id.menu_store)
            menuProduct = rootView.findViewById(R.id.menu_product)
            menuStock = rootView.findViewById(R.id.menu_stock)

            menuHotel = rootView.findViewById(R.id.menu_hotel)
            menuRoom = rootView.findViewById(R.id.menu_room)
            menuBook = rootView.findViewById(R.id.menu_book)

            menuTravel = rootView.findViewById(R.id.menu_travel)
            menuNetwork = rootView.findViewById(R.id.menu_network)
            menuService = rootView.findViewById(R.id.menu_service)


//            menuNorth.setMenuClickListener(this, "")
//
//            menuNorth.setMenuClickListener(this, "north")
//            menuEast.setMenuClickListener(this, "east")
//            menuWest.setMenuClickListener(this, "west")
//            menu_south.setMenuClickListener(this, "south")
//            menu_northeast.setMenuClickListener(this, "northeast")
//
//            menu_market.setMenuClickListener(this, "market")
//            menu_zone.setMenuClickListener(this, "zone")
//            menu_store.setMenuClickListener(this, "store")
//            menu_product.setMenuClickListener(this, "product")
//            menu_stock.setMenuClickListener(this, "stock")
//
//            menu_hotel.setMenuClickListener(this, "hotel")
//            menu_room.setMenuClickListener(this, "room")
//            menu_book.setMenuClickListener(this, "book")
//
//            menu_travel.setMenuClickListener(this, "travel")
//            menu_network.setMenuClickListener(this, "network")
//            menu_service.setMenuClickListener(this, "service")
        }
    }

    override fun onPhotoClick(v: View?, tag: String?) {
        Log.d("Home", "Menu click " + tag)
        if (tag.equals("north") || tag.equals("east") || tag.equals("west") ||
            tag.equals("south") || tag.equals("northeast")) {

        } else {
//            val intent = Intent(this@Home, ManageActivity::class.java)
//            intent.putExtra("category", tag)
//            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TestFragment().apply {
                arguments = Bundle().apply {
                    //                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
