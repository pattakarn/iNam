package com.sungkunn.inam.fragment.ui.market

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.RV_Adapter_Home_Market
import com.sungkunn.inam.model.Travel
import com.sungkunn.inam.model.WrapTravel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MarketTravelFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MarketTravelFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MarketTravelFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val db = FirebaseFirestore.getInstance()
    var travelList: MutableList<Any>? = null

    var rv: RecyclerView? = null

    var TAG = "Market Travel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()
        getTravel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_market_travel, container, false)
        rv = rootView.findViewById(R.id.rv)

        return rootView
    }

    fun getTravel() {
        travelList = ArrayList()
//        mMarket = ArrayList()
        db.collection("travels")
//            .whereEqualTo("marketId", marketId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    travelList!!.add(WrapTravel(document.id, document.toObject(Travel::class.java)))
//                    mMarket!!.add(document.toObject(Market::class.java).name.toString())
                }
                setRV()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun setRV() {
        val adapter = RV_Adapter_Home_Market(travelList, fragmentManager!!)
        val llm = LinearLayoutManager(context)

        rv!!.setLayoutManager(llm)
        rv!!.setAdapter(adapter)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarketTravelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
