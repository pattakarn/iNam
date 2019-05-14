package com.sungkunn.inam.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungkunn.inam.R
import com.sungkunn.inam.adapter.RV_Adapter_Market
import com.sungkunn.inam.db.Market

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TravelFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TravelFragment : Fragment() {
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
        var rootView = inflater.inflate(R.layout.fragment_travel, container, false)
        var rv = rootView.findViewById<RecyclerView>(R.id.travel_rv)

        var mData = resources.getStringArray(R.array.region_data)
//        var adapSpin = ArrayAdapter<String>(inflater.context, android.R.layout.simple_spinner_dropdown_item, mData)
//        spin_region.adapter = adapSpin

        var listData = ArrayList<Market>()
        listData.add(Market("Market 1"))
        listData.add(Market("Market 2"))
        listData.add(Market("Market 3"))
        listData.add(Market("Market 4"))
        listData.add(Market("Market 5"))
        listData.add(Market("Market 6"))
        listData.add(Market("Market 7"))
        listData.add(Market("Market 8"))
        listData.add(Market("Market 9"))
        listData.add(Market("Market 10"))

        val adapMarket = RV_Adapter_Market(listData)
        val llm = LinearLayoutManager(inflater.context)
        val glm = GridLayoutManager(inflater.context, 2)

        rv.layoutManager = glm
        rv.adapter = adapMarket


        return rootView
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TravelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TravelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
