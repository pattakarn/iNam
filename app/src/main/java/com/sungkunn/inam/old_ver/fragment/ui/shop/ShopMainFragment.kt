package com.sungkunn.inam.old_ver.fragment.ui.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.adapter.Pager_Adapter_Title
import com.sungkunn.inam.old_ver.fragment.ui.market.MarketShopFragment

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
    var backdrop: ImageView? = null

    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null

    var TAG = "Shop Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            key = it.getString("key")
            name = it.getString("name")
            type = it.getString("type")
        }
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
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
        backdrop = rootView.findViewById(R.id.backdrop)

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

//        Log.d(TAG, currentUser!!.uid + " === " + currentUser!!.email)
        setPhoto()
        pager!!.adapter = adapter
        pager!!.setOnTouchListener(View.OnTouchListener { v, event -> true })


        return rootView
    }

    fun setPhoto() {
        val storageRef = FirebaseStorage.getInstance().reference
//        Log.d(TAG, "Key =============================================== " + key)
        storageRef.child("images/" + key + "_0").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this)
                .load(it.toString())
                .placeholder(R.drawable.ic_region)
                .into(backdrop!!)
//            Log.d(TAG, "Success ===============================================")
        }.addOnFailureListener {
            // Handle any errors
//            Log.d(TAG, "Error ===============================================")
        }
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
