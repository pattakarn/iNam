package com.sungkunn.inam.old_ver.fragment.manage.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.old_ver.adapter.RV_Adapter_Manage_List
import com.sungkunn.inam.old_ver.model.Market
import com.sungkunn.inam.old_ver.model.WrapMarket
import com.sungkunn.inam.old_ver.fragment.manage.item.MarketItemFragment

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MarketListFragment.OnListFragmentInteractionListener] interface.
 */
class MarketListFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {


    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<Any>? = null

    var toolbar: Toolbar? = null
    var rv: RecyclerView? = null

    var TAG = "Market List"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
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
        val rootView = inflater.inflate(R.layout.fragment_marketlist_list, container, false)
        rv = rootView.findViewById(R.id.list)
        toolbar = rootView.findViewById(R.id.toolbar)

        toolbar!!.inflateMenu(R.menu.menu_manage)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)


//        var listItem = arrayOf("Market 1", "Market 2", "Market 3")


        return rootView
    }

    fun getMarket() {
        marketList = ArrayList()
        db.collection("markets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    marketList!!.add(WrapMarket(document.id, document.toObject(Market::class.java)))
                }
                setRV()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun setRV() {
        val adapter = RV_Adapter_Manage_List(marketList, fragmentManager!!)
        val llm = LinearLayoutManager(context)

        rv!!.setLayoutManager(llm)
        rv!!.setAdapter(adapter)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_add ->
                fragmentManager!!.beginTransaction()
                    .replace(R.id.container_manage, MarketItemFragment.newInstance(null))
                    .addToBackStack(null)
                    .commit()
//                        MarketItemFragment.display(fragmentManager)
        }
        return true
    }

    override fun onClick(v: View?) {
        Log.d("ManageFragment", "close fragment")
        //fragmentManager!!.popBackStack()
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MarketListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
