package com.sungkunn.inam.fragment.manage.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.adapter.RV_Adapter_Manage_List
import com.sungkunn.inam.db.Market
import com.sungkunn.inam.db.WrapMarket
import com.sungkunn.inam.db.WrapZone
import com.sungkunn.inam.db.Zone
import com.sungkunn.inam.fragment.manage.item.ZoneItemFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ZoneListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ZoneListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ZoneListFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<WrapMarket>? = null
    var zoneList: MutableList<Any>? = null

    var mMarket: ArrayList<String>? = null

    var toolbar: Toolbar? = null
    var spinMarket: Spinner? = null
    var rv: RecyclerView? = null

    var TAG = "Zone List"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()
        getMarket()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(com.sungkunn.inam.R.layout.fragment_zone_list, container, false)
        rv = rootView.findViewById(com.sungkunn.inam.R.id.rv)
        toolbar = rootView.findViewById(com.sungkunn.inam.R.id.toolbar)
        spinMarket = rootView.findViewById(com.sungkunn.inam.R.id.spin)

        toolbar!!.inflateMenu(com.sungkunn.inam.R.menu.menu_manage)
        toolbar!!.setNavigationIcon(com.sungkunn.inam.R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

//        ArrayAdapter.createFromResource(
//            inflater.context,
//            com.sungkunn.inam.R.array.more_menu,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinMarket!!.adapter = adapter
//        }


//        var listItem = arrayOf("Zone 1", "Zone 2", "Zone 3")
//
//        val adapter = RV_Adapter_Manage_Main(listItem, fragmentManager!!)
//        val llm = LinearLayoutManager(inflater.context)
//
//        rv.setLayoutManager(llm)
//        rv.setAdapter(adapter)

        return rootView
    }

    fun getMarket() {
        marketList = ArrayList()
        mMarket = ArrayList()
        db.collection("markets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    marketList!!.add(WrapMarket(document.id, document.toObject(Market::class.java)))
                    mMarket!!.add(document.toObject(Market::class.java).name.toString())
                }
                setSpinner()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getZone(marketId: String?) {
        zoneList = ArrayList()
        db.collection("zones")
            .whereEqualTo("marketId", marketId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    zoneList!!.add(WrapZone(document.id, document.toObject(Zone::class.java)))
                }
                setRV()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun setSpinner() {
        val adapMarket = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mMarket)
        spinMarket!!.setAdapter(adapMarket)
        spinMarket!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                getZone(marketList!!.get(position).key)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                if (marketList != null)
                    getZone(marketList!!.get(0).key)
            }
        }

    }

    fun setRV() {
        val adapter = RV_Adapter_Manage_List(zoneList, fragmentManager!!)
        val llm = LinearLayoutManager(context)

        rv!!.setLayoutManager(llm)
        rv!!.setAdapter(adapter)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            com.sungkunn.inam.R.id.action_add ->
                fragmentManager!!.beginTransaction()
                    .replace(com.sungkunn.inam.R.id.container_manage, ZoneItemFragment.newInstance(null))
                    .addToBackStack(null)
                    .commit()
//                        MarketItemFragment.display(fragmentManager)
        }
        return true
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "close fragment")
        //fragmentManager!!.popBackStack()
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZoneListFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

