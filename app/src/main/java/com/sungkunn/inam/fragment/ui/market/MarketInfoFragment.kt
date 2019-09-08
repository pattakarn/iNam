package com.sungkunn.inam.fragment.ui.market

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.example.MapsTestActivity
import com.sungkunn.inam.model.Market
import com.sungkunn.inam.model.WrapMarket






// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MarketInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MarketInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MarketInfoFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    var vMap: View? = null

    val db = FirebaseFirestore.getInstance()
    var key: String? = null
    var name: String? = null
    var type: String? = null
    var data: WrapMarket? = null

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

    var TAG = "Market Info"


    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        var rootView = inflater.inflate(R.layout.fragment_market_info, container, false)

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

        getMarket()

        vMap = rootView.findViewById(com.sungkunn.inam.R.id.v_map)
        vMap?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(inflater.context, MapsTestActivity::class.java)
////                    intent.putExtra("category", items.get(position))
                    inflater.context.startActivity(intent)
            }
        })

        return rootView
    }

    fun getMarket() {
        db.collection("markets").document(key!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    WrapShop(document.id, document.toObject(Shop::class.java))
                    data = WrapMarket(document.id, document.toObject(Market::class.java)!!)
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

    fun setValue(){
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager
            .findFragmentById(com.sungkunn.inam.R.id.map_item) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }




    companion object {
        @JvmStatic
        fun newInstance(key: String?, name: String?, type: String?) =
            MarketInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        Log.i(TAG, "onAttach")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }
}
