package com.sungkunn.inam.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.RV_Adapter_Home_Market
import com.sungkunn.inam.model.Market
import com.sungkunn.inam.model.WrapMarket

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeMarketFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeMarketFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeMarketFragment : Fragment(), View.OnClickListener {

    private var category: String? = null

    val db = FirebaseFirestore.getInstance()
    var marketList: MutableList<Any>? = null
    var mMarket: ArrayList<String>? = null

    var rv: RecyclerView? = null
    var toolbar: Toolbar? = null
//    var pager: ViewPager? = null

    var backdrop: ImageView? = null

    var TAG = "Home Market"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
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
        var rootView = inflater.inflate(R.layout.fragment_home_market, container, false)
        rv = rootView.findViewById<RecyclerView>(R.id.rv)
        toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
//        pager = rootView.findViewById(R.id.pager_photo)

        toolbar!!.setTitle(category!!.toUpperCase())
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)

        backdrop = rootView.findViewById(R.id.backdrop)

//        setPhotoPage()
//        var listItem = this.resources.getStringArray(R.array.region_data)
//        val adapter = RV_Adapter_Home_Market(marketList, fragmentManager!!)
//        val llm = LinearLayoutManager(context)
//
//        rv!!.setLayoutManager(llm)
//        rv!!.setAdapter(adapter)
        return rootView
    }

//    private fun setPhotoPage(){
//        val adapter = Pager_Adapter_Title(this!!.fragmentManager!!)
////        tabs!!.setupWithViewPager(pager)
//        adapter.addFragment(PhotoPagerFragment.newInstance("", ""), "Photo")
//        adapter.addFragment(PhotoPagerFragment.newInstance("", ""), "Photo")
//        pager!!.adapter = adapter
////        pager!!.setOnTouchListener(View.OnTouchListener { v, event -> true })
//    }

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
                setRV()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception as Throwable?)
            }
    }

    fun setRV() {
        val adapter = RV_Adapter_Home_Market(marketList, fragmentManager!!)
        val llm = LinearLayoutManager(context)

        rv!!.setLayoutManager(llm)
        rv!!.setAdapter(adapter)
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
        fun newInstance(category: String) =
            HomeMarketFragment().apply {
                arguments = Bundle().apply {
                    putString("category", category)
                }
            }
    }
}
