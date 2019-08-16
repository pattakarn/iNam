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
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.RV_Adapter_Manage_List
import com.sungkunn.inam.model.Market
import com.sungkunn.inam.model.Network
import com.sungkunn.inam.model.WrapMarket
import com.sungkunn.inam.model.WrapNetwork
import com.sungkunn.inam.fragment.manage.item.NetworkItemFragment

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
class NetworkListFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private var param1: String? = null
    private var param2: String? = null

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<WrapMarket>? = null
    var networkList: MutableList<Any>? = null

    var mMarket: ArrayList<String>? = null

    var toolbar: Toolbar? = null
    var spinMarket: Spinner? = null
    var rv: RecyclerView? = null

    var TAG = "Network List"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
        var rootView = inflater.inflate(R.layout.fragment_network_list, container, false)
        rv = rootView.findViewById<RecyclerView>(R.id.rv)
        toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        spinMarket = rootView.findViewById<Spinner>(R.id.spin_market)

        toolbar!!.inflateMenu(R.menu.menu_manage)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)

//        ArrayAdapter.createFromResource(
//            inflater.context,
//            R.array.more_menu,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinMarket.adapter = adapter
//        }
//
//
//        var listItem = arrayOf("Network 1", "Network 2", "Network 3")
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

    fun getNetwork(marketId: String?) {
        networkList = ArrayList()
        db.collection("networks")
            .whereEqualTo("marketId", marketId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    networkList!!.add(WrapNetwork(document.id, document.toObject(Network::class.java)))
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
                getNetwork(marketList!!.get(position).key)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                if (marketList != null)
                    getNetwork(marketList!!.get(0).key)
            }
        }

    }

    fun setRV() {
        val adapter = RV_Adapter_Manage_List(networkList, fragmentManager!!)
        val llm = LinearLayoutManager(context)

        rv!!.setLayoutManager(llm)
        rv!!.setAdapter(adapter)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_add ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, NetworkItemFragment.newInstance(null))
                    .addToBackStack(null)
                    .commit()
//                        MarketItemFragment.display(fragmentManager)
        }
        return true
    }

    override fun onClick(v: View?) {
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
            NetworkListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
