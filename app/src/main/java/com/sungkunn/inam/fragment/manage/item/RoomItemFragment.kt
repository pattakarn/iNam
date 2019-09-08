package com.sungkunn.inam.fragment.manage.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.sungkunn.inam.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MarketItemFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MarketItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RoomItemFragment : Fragment(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_room_item, container, false)
        var toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        var spinMarket = rootView.findViewById<Spinner>(R.id.spin_market)
        var spinHotel = rootView.findViewById<Spinner>(R.id.spin_hotel)
        var spinGuest = rootView.findViewById<Spinner>(R.id.spin_guest)
        var spinBed = rootView.findViewById<Spinner>(R.id.spin_bed)

        toolbar!!.inflateMenu(R.menu.menu_item)
        toolbar!!.setNavigationIcon(R.drawable.ic_close_white)
        toolbar!!.setNavigationOnClickListener(this)
        toolbar!!.setOnMenuItemClickListener(this)
//        fragmentManager!!.beginTransaction()
//            .replace(R.id.marketItemContainer, MarketItemPreferenceFragment())
//            .addToBackStack(null)
//            .commit()
        ArrayAdapter.createFromResource(
            inflater.context,
            R.array.more_menu,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinMarket.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            inflater.context,
            R.array.more_menu,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinHotel.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            inflater.context,
            R.array.guest_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinGuest.adapter = adapter
        }


        ArrayAdapter.createFromResource(
            inflater.context,
            R.array.bed_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinBed.adapter = adapter
        }

        return rootView
    }

    override fun onClick(v: View?) {
        //fragmentManager!!.popBackStack()
        if (fragmentManager!!.backStackEntryCount > 0) {
            fragmentManager!!.popBackStack()
        } else {
            activity!!.finish()
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_save ->
                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
        }
        return true
    }

//    private fun getPreview() {
//        fragmentManager!!.beginTransaction()
//            .replace(R.id.container_manage, PreviewFragment.newInstance(productItem!!.key, productItem!!.data.name.toString()))
//            .addToBackStack(null)
//            .commit()
//    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RoomItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
