package com.sungkunn.inam.old_ver.fragment.ui.shop

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.model.Shop
import com.sungkunn.inam.old_ver.model.WrapShop

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShopInfoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val db = FirebaseFirestore.getInstance()
    var key: String? = null
    var name: String? = null
    var type: String? = null
    var data: WrapShop? = null

    var infoDetail: TextView? = null
    var contactOwner: TextView? = null
    var contactLink: TextView? = null
    var contactPhone: TextView? = null
    var contactLine: TextView? = null
    var contactFacebook: TextView? = null
    var contactEmail: TextView? = null

    var tvOpentimeMonday: TextView? = null
    var tvOpentimeTuesday: TextView? = null
    var tvOpentimeWednesday: TextView? = null
    var tvOpentimeThursday: TextView? = null
    var tvOpentimeFriday: TextView? = null
    var tvOpentimeSaturday: TextView? = null
    var tvOpentimeSunday: TextView? = null


    var TAG = "Shop Info"

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
        var rootView = inflater.inflate(R.layout.fragment_shop_info, container, false)
        infoDetail = rootView.findViewById(R.id.info_detail)
        contactOwner = rootView.findViewById(R.id.tv_contact_owner)
        contactLink = rootView.findViewById(R.id.tv_contact_link)
        contactPhone = rootView.findViewById(R.id.tv_contact_phone)
        contactLine = rootView.findViewById(R.id.tv_contact_line)
        contactFacebook = rootView.findViewById(R.id.tv_contact_fb)
        contactEmail = rootView.findViewById(R.id.tv_contact_email)

        tvOpentimeMonday = rootView.findViewById(R.id.tv_opentime_monday)
        tvOpentimeTuesday = rootView.findViewById(R.id.tv_opentime_tuesday)
        tvOpentimeWednesday = rootView.findViewById(R.id.tv_opentime_wednesday)
        tvOpentimeThursday = rootView.findViewById(R.id.tv_opentime_thursday)
        tvOpentimeFriday = rootView.findViewById(R.id.tv_opentime_friday)
        tvOpentimeSaturday = rootView.findViewById(R.id.tv_opentime_saturday)
        tvOpentimeSunday = rootView.findViewById(R.id.tv_opentime_sunday)

        getShop()



        return rootView
    }

    fun getShop() {
        db.collection("shops").document(key!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    WrapShop(document.id, document.toObject(Shop::class.java))
                    data = WrapShop(document.id, document.toObject(Shop::class.java)!!)
//                    infoDetail!!.setText(document.toObject(Shop::class.java)!!.name)
                    setValue()
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    fun setValue() {
        infoDetail!!.setText(data!!.data.name)

        contactOwner!!.setText(data!!.data.owner)
        contactPhone!!.setText(data!!.data.phone)
        contactLine!!.setText(data!!.data.line)
        contactFacebook!!.setText(data!!.data.facebook)
        contactEmail!!.setText(data!!.data.email)

        tvOpentimeMonday!!.setText(checkOpentime(data!!.data.monday_open, data!!.data.monday_close))
        tvOpentimeTuesday!!.setText(checkOpentime(data!!.data.tuesday_open, data!!.data.tuesday_close))
        tvOpentimeWednesday!!.setText(checkOpentime(data!!.data.wednesday_open, data!!.data.wednesday_close))
        tvOpentimeThursday!!.setText(checkOpentime(data!!.data.thursday_open, data!!.data.thursday_close))
        tvOpentimeFriday!!.setText(checkOpentime(data!!.data.friday_open, data!!.data.friday_close))
        tvOpentimeSaturday!!.setText(checkOpentime(data!!.data.saturday_open, data!!.data.saturday_close))
        tvOpentimeSunday!!.setText(checkOpentime(data!!.data.sunday_open, data!!.data.sunday_close))

    }

    fun checkOpentime(open: String?, close: String?): String {
        var result = ""
        if (open.equals("") && close.equals("")) {
            result = getString(R.string.title_opentime_close)
        } else {
            result = open + " - " + close
        }
        return result
    }

    companion object {
        @JvmStatic
        fun newInstance(key: String?, name: String?, type: String?) =
            ShopInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }
}
