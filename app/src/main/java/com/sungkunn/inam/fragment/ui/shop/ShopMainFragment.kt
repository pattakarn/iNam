package com.sungkunn.inam.fragment.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.Pager_Adapter_Title
import com.sungkunn.inam.fragment.ui.market.MarketShopFragment
import com.sungkunn.inam.model.Shop

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShopMainFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShopMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShopMainFragment : Fragment(), View.OnClickListener {


    val db = FirebaseFirestore.getInstance()
    var key: String? = null
    var name: String? = null
    var type: String? = null

    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var tabs: TabLayout? = null
    var pager: ViewPager? = null

    var dataShop: Shop? = null

    var TAG = "Shop Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            key = it.getString("key")
            name = it.getString("name")
            type = it.getString("type")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_shop_main, container, false)
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        tabs = rootView.findViewById(R.id.tabs)
        pager = rootView.findViewById(R.id.pager_shop)

        toolbar!!.setTitle(name)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)

        val adapter = Pager_Adapter_Title(this!!.fragmentManager!!)
        tabs!!.setupWithViewPager(pager)
        adapter.addFragment(ShopInfoFragment.newInstance(key, name, type), "Info")
        when (type) {
            "shop" ->
                adapter.addFragment(ShopProductFragment.newInstance("", ""), "Product")
            "hotel" ->
                adapter.addFragment(MarketShopFragment.newInstance("", ""), "Room")
        }

//        when (items) {
//            is WrapShop -> {
//                val data = items as WrapShop
//                toolbar!!.setTitle(data.data.name)
//                adapter.addFragment(MarketInfoFragment.newInstance("", ""), "Info")
//                adapter.addFragment(MarketShopFragment.newInstance("", ""), "Product")
//            }
//            is WrapHotel -> {
//                val data = items as WrapHotel
//                toolbar!!.setTitle(data.data.name)
//                adapter.addFragment(MarketInfoFragment.newInstance("", ""), "Info")
//                adapter.addFragment(MarketShopFragment.newInstance("", ""), "Room")
//            }
//        }


        pager!!.adapter = adapter
        pager!!.setOnTouchListener(View.OnTouchListener { v, event -> true })


        return rootView
    }



    override fun onClick(v: View?) {
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(key: String?, name: String?, type: String?) =
            ShopMainFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }
}
