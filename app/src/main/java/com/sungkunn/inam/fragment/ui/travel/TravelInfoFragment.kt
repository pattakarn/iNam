package com.sungkunn.inam.fragment.ui.travel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.model.Travel
import com.sungkunn.inam.model.WrapTravel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TravelInfoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TravelInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TravelInfoFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    var key: String? = null
    var name: String? = null
    var type: String? = null
    var data: WrapTravel? = null

    var infoDetail: TextView? = null
    var contactLink: TextView? = null
    var contactPhone: TextView? = null
    var contactLine: TextView? = null
    var contactFacebook: TextView? = null
    var contactEmail: TextView? = null

    var TAG = "Travel Info"

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
        var rootView = inflater.inflate(R.layout.fragment_travel_info, container, false)
        infoDetail = rootView.findViewById(R.id.info_detail)
        contactLink = rootView.findViewById(R.id.tv_contact_link)
        contactPhone = rootView.findViewById(R.id.tv_contact_phone)
        contactLine = rootView.findViewById(R.id.tv_contact_line)
        contactFacebook = rootView.findViewById(R.id.tv_contact_fb)
        contactEmail = rootView.findViewById(R.id.tv_contact_email)

        getTravel()



        return rootView
    }

    fun getTravel() {
        db.collection("travels").document(key!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                    WrapShop(document.id, document.toObject(Shop::class.java))
                    data = WrapTravel(document.id, document.toObject(Travel::class.java)!!)
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

        contactPhone!!.setText(data!!.data.phone)
        contactLine!!.setText(data!!.data.line)
        contactFacebook!!.setText(data!!.data.facebook)
        contactEmail!!.setText(data!!.data.email)


    }

    companion object {
        @JvmStatic
        fun newInstance(key: String?, name: String?, type: String?) =
            TravelInfoFragment().apply {
                arguments = Bundle().apply {
                    putString("key", key)
                    putString("name", name)
                    putString("type", type)
                }
            }
    }
}
