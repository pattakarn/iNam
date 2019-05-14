package com.sungkunn.inam.fragment.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.Pager_Adapter_Title
import com.sungkunn.inam.db.WrapMarket
import com.sungkunn.inam.fragment.manage.item.NetworkItemFragment
import com.sungkunn.inam.fragment.manage.item.ServiceItemFragment
import com.sungkunn.inam.fragment.manage.list.HotelListFragment
import com.sungkunn.inam.fragment.manage.list.TravelListFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShowMainFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShowMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShowMainFragment : Fragment(), View.OnClickListener {

    var marketItem: WrapMarket? = null

    var toolbar: Toolbar? = null
    var ll: LinearLayout? = null
    var tabs: TabLayout? = null
    var pager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            marketItem = it.getParcelable("marketItem")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_show_main, container, false)
        toolbar = rootView.findViewById(R.id.toolbar)
        ll = rootView.findViewById(R.id.ll)
        tabs = rootView.findViewById(R.id.tabs)
        pager = rootView.findViewById(R.id.pager_main)

        toolbar!!.setTitle(marketItem!!.data.name)
        toolbar!!.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar!!.setNavigationOnClickListener(this)

        val adapter = Pager_Adapter_Title(this!!.fragmentManager!!)
        tabs!!.setupWithViewPager(pager)
        adapter.addFragment(HotelListFragment.newInstance("", ""), "Hotel")
        adapter.addFragment(TravelListFragment.newInstance("", ""), "Travel")
        adapter.addFragment(NetworkItemFragment.newInstance(null), "Network")
        adapter.addFragment(ServiceItemFragment.newInstance(null), "Service")
        pager!!.adapter = adapter
        pager!!.setOnTouchListener(View.OnTouchListener { v, event -> true })


        return rootView
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
        fun newInstance(marketItem: WrapMarket?) =
            ShowMainFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("marketItem", marketItem)
                }
            }
    }
}
