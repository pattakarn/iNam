package com.sungkunn.inam.fragment.ui.hotel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.model.Hotel
import com.sungkunn.inam.model.WrapHotel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HotelInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HotelInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HotelInfoFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    var key: String? = null
    var name: String? = null
    var type: String? = null
    var data: WrapHotel? = null

    var infoDetail: TextView? = null
    var contactOwner: TextView? = null
    var contactLink: TextView? = null
    var contactPhone: TextView? = null
    var contactLine: TextView? = null
    var contactFacebook: TextView? = null
    var contactEmail: TextView? = null


    var TAG = "Hotel Info"

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
        var rootView = inflater.inflate(R.layout.fragment_hotel_info, container, false)
        infoDetail = rootView.findViewById(R.id.info_detail)
        contactOwner = rootView.findViewById(R.id.tv_contact_owner)
        contactLink = rootView.findViewById(R.id.tv_contact_link)
        contactPhone = rootView.findViewById(R.id.tv_contact_phone)
        contactLine = rootView.findViewById(R.id.tv_contact_line)
        contactFacebook = rootView.findViewById(R.id.tv_contact_fb)
        contactEmail = rootView.findViewById(R.id.tv_contact_email)

        getHotel()



        return rootView
    }

    fun getHotel() {
        db.collection("hotels").document(key!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    WrapShop(document.id, document.toObject(Shop::class.java))
                    data = WrapHotel(document.id, document.toObject(Hotel::class.java)!!)
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


    }


    companion object {
        @JvmStatic
        fun newInstance(key: String?, name: String?, type: String?) =
            HotelInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }
}
